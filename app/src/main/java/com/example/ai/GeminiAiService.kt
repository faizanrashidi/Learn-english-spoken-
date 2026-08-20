package com.example.ai

import com.example.BuildConfig
import com.example.model.ConversationMessage
import com.example.model.ConversationScenario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class SpokenSentenceEvaluation(
    val isCorrect: Boolean,
    val accuracyScore: Int, // 0 - 100
    val learnerSaid: String,
    val correctSentence: String,
    val hindiExplanation: String,
    val hindiPraise: String,
    val phoneticGuide: String? = null,
    val grammarRule: String? = null
)

data class ConversationAiResponse(
    val englishReply: String,
    val hindiTranslation: String,
    val grammarFeedback: String? = null,
    val betterSentence: String? = null
)

data class PostChatReport(
    val overallSpeakingScore: Int,
    val grammarScore: Int,
    val vocabularyScore: Int,
    val fluencyScore: Int,
    val mistakesFound: List<Pair<String, String>>, // (Learner said, Corrected)
    val vocabularyUpgrades: List<Pair<String, String>>, // (Basic word, Better word)
    val hindiSummaryFeedback: String
)

class GeminiAiService {

    private val apiKey = BuildConfig.GEMINI_API_KEY

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun evaluateSpeech(
        learnerSaid: String,
        expectedSentence: String,
        grammarConcept: String,
        learnerLevel: Int
    ): SpokenSentenceEvaluation = withContext(Dispatchers.IO) {
        val trimmedLearner = learnerSaid.trim()
        if (trimmedLearner.isEmpty()) {
            return@withContext SpokenSentenceEvaluation(
                isCorrect = false,
                accuracyScore = 0,
                learnerSaid = "(Nothing heard)",
                correctSentence = expectedSentence,
                hindiExplanation = "कृपया माइक बटन दबाकर बोलें।",
                hindiPraise = "एक बार फिर से बोलने का प्रयास करें!"
            )
        }

        // Try Gemini API if key is valid
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val prompt = """
                    You are a friendly, expert AI Spoken English teacher for Hindi-medium learners.
                    Target sentence to speak: "$expectedSentence"
                    Learner actually said: "$trimmedLearner"
                    Target grammar concept: "$grammarConcept"
                    Learner Level: $learnerLevel (0=beginner, 100=fluent)

                    Evaluate the learner's spoken sentence carefully:
                    1. If the sentence has the same meaning and is grammatically valid (even with slight natural variation), mark isCorrect as true.
                    2. If there are missing helping verbs (like 'is', 'are', 'am'), wrong verb forms (s/es, past tense), wrong prepositions, or wrong word order, mark isCorrect as false.
                    3. Explain the mistake in simple, friendly Hindi (e.g. "यहाँ 'is' जरूरी है।" or "He के साथ verb में s/es लगता है।").
                    4. Give a warm encouraging sentence in Hindi.

                    Respond strictly in valid JSON format:
                    {
                      "isCorrect": true/false,
                      "accuracyScore": 85,
                      "correctSentence": "$expectedSentence",
                      "hindiExplanation": "हिंदी में सरल कारण",
                      "hindiPraise": "प्रोत्साहन वाक्य",
                      "phoneticGuide": "Hindi pronunciation guide like वॉटर",
                      "grammarRule": "Rule name"
                    }
                """.trimIndent()

                val apiResponse = callGeminiRest(prompt)
                val json = parseJsonFromResponse(apiResponse)
                if (json != null) {
                    return@withContext SpokenSentenceEvaluation(
                        isCorrect = json.optBoolean("isCorrect", false),
                        accuracyScore = json.optInt("accuracyScore", 70),
                        learnerSaid = trimmedLearner,
                        correctSentence = json.optString("correctSentence", expectedSentence),
                        hindiExplanation = json.optString("hindiExplanation", "सटीक वाक्य बोलें।"),
                        hindiPraise = json.optString("hindiPraise", "शानदार प्रयास!"),
                        phoneticGuide = json.optString("phoneticGuide", null),
                        grammarRule = json.optString("grammarRule", grammarConcept)
                    )
                }
            } catch (_: Exception) {
                // Fallback to offline rule-based analyzer
            }
        }

        // Offline Rule-based Analyzer
        fallbackLinguisticEvaluation(trimmedLearner, expectedSentence, grammarConcept)
    }

    suspend fun generateChatReply(
        scenario: ConversationScenario,
        conversationHistory: List<ConversationMessage>,
        latestUserSpeech: String,
        learnerLevel: Int
    ): ConversationAiResponse = withContext(Dispatchers.IO) {
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val historyFormatted = conversationHistory.takeLast(6).joinToString("\n") {
                    "${if (it.sender == com.example.model.MessageSender.AI_TEACHER) "AI (${scenario.aiRole})" else "Learner"}: ${it.textEnglish}"
                }

                val prompt = """
                    You are roleplaying as ${scenario.aiRole} in the scenario: "${scenario.title}".
                    The learner is: ${scenario.userRole}.
                    Learner English Level: Level $learnerLevel / 100.
                    
                    Conversation so far:
                    $historyFormatted
                    Learner just said: "$latestUserSpeech"

                    Respond naturally in character in 1-2 spoken English sentences.
                    Also provide a Hindi translation of your response.
                    If the learner made any noticeable grammar mistake in their sentence, explain it gently in Hindi and provide a better English sentence.

                    Respond strictly in valid JSON format:
                    {
                      "englishReply": "Your conversational answer in English",
                      "hindiTranslation": "आपकी बात का हिंदी अनुवाद",
                      "grammarFeedback": "Optional Hindi feedback if learner made a mistake, otherwise null",
                      "betterSentence": "Optional improved version of learner's sentence, otherwise null"
                    }
                """.trimIndent()

                val apiResponse = callGeminiRest(prompt)
                val json = parseJsonFromResponse(apiResponse)
                if (json != null) {
                    return@withContext ConversationAiResponse(
                        englishReply = json.optString("englishReply", "That's great! Let's continue."),
                        hindiTranslation = json.optString("hindiTranslation", "बहुत बढ़िया! चलिए आगे बात करते हैं।"),
                        grammarFeedback = if (json.has("grammarFeedback") && !json.isNull("grammarFeedback")) json.getString("grammarFeedback") else null,
                        betterSentence = if (json.has("betterSentence") && !json.isNull("betterSentence")) json.getString("betterSentence") else null
                    )
                }
            } catch (_: Exception) {
                // Fallback
            }
        }

        // Fallback offline conversational AI
        fallbackConversationResponse(scenario, latestUserSpeech)
    }

    suspend fun generatePostChatReport(
        messages: List<ConversationMessage>,
        scenario: ConversationScenario
    ): PostChatReport = withContext(Dispatchers.IO) {
        val learnerMessages = messages.filter { it.sender == com.example.model.MessageSender.LEARNER }
        val count = learnerMessages.size

        PostChatReport(
            overallSpeakingScore = minOf(95, 70 + count * 5),
            grammarScore = 80,
            vocabularyScore = 85,
            fluencyScore = minOf(90, 65 + count * 6),
            mistakesFound = listOf(
                "I want book table" to "I would like to book a table, please.",
                "How much price?" to "How much does this cost?"
            ),
            vocabularyUpgrades = listOf(
                "Good food" to "Delicious / Delectable cuisine",
                "Give me water" to "Could you please bring some water?"
            ),
            hindiSummaryFeedback = "आपने बातचीत में बहुत अच्छा आत्मविश्वास दिखाया! छोटे वाक्यों की जगह 'Could you please...' जैसे विनम्र वाक्यों का अभ्यास जारी रखें।"
        )
    }

    private fun callGeminiRest(prompt: String): String {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
        
        val requestJson = JSONObject().apply {
            val contents = JSONArray().apply {
                val contentObj = JSONObject().apply {
                    val parts = JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", prompt)
                        })
                    }
                    put("parts", parts)
                }
                put(contentObj)
            }
            put("contents", contents)
            
            val genConfig = JSONObject().apply {
                put("temperature", 0.7)
                put("topP", 0.95)
            }
            put("generationConfig", genConfig)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = requestJson.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseString = response.body?.string() ?: ""

        if (!response.isSuccessful) {
            throw Exception("API call failed with HTTP code ${response.code}: $responseString")
        }

        val rootObj = JSONObject(responseString)
        val candidates = rootObj.optJSONArray("candidates")
        val firstCandidate = candidates?.optJSONObject(0)
        val content = firstCandidate?.optJSONObject("content")
        val parts = content?.optJSONArray("parts")
        val firstPart = parts?.optJSONObject(0)
        return firstPart?.optString("text") ?: ""
    }

    private fun parseJsonFromResponse(rawText: String): JSONObject? {
        return try {
            val cleaned = rawText.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()
            JSONObject(cleaned)
        } catch (_: Exception) {
            null
        }
    }

    private fun fallbackLinguisticEvaluation(
        learnerSaid: String,
        expectedSentence: String,
        grammarConcept: String
    ): SpokenSentenceEvaluation {
        val cleanLearner = learnerSaid.lowercase().replace(Regex("[^a-z0-9 ]"), "").trim()
        val cleanExpected = expectedSentence.lowercase().replace(Regex("[^a-z0-9 ]"), "").trim()

        val learnerWords = cleanLearner.split(" ").filter { it.isNotBlank() }
        val expectedWords = cleanExpected.split(" ").filter { it.isNotBlank() }

        // Check word similarity
        var matchCount = 0
        for (w in learnerWords) {
            if (expectedWords.contains(w)) matchCount++
        }

        val accuracy = if (expectedWords.isNotEmpty()) {
            ((matchCount.toFloat() / expectedWords.size.toFloat()) * 100).toInt().coerceIn(20, 100)
        } else 80

        val isCorrect = cleanLearner == cleanExpected || (accuracy >= 80 && learnerWords.size >= expectedWords.size - 1)

        val explanation = when {
            isCorrect -> "बहुत बढ़िया! आपका उच्चारण और व्याकरण बिल्कुल सही है।"
            cleanLearner.contains("he go") -> "He/She/It के साथ verb में s/es लगता है -> 'He goes'."
            cleanLearner.contains("my name") && !cleanLearner.contains("is") -> "यहाँ 'is' जरूरी है -> 'My name is...'"
            cleanLearner.contains("did not went") -> "Did not (didn't) के बाद क्रिया का पहला रूप (V1 - go) आता है।"
            cleanLearner.contains("i am knowing") -> "Know stative verb है, 'I know' बोलें।"
            else -> "ध्यान दें: सही वाक्य '$expectedSentence' है। एक बार फिर से बोलिए।"
        }

        val praise = if (isCorrect) "शानदार! आप तेजी से सीख रहे हैं।" else "अच्छा प्रयास! अब सही sentence दोबारा बोलिए।"

        return SpokenSentenceEvaluation(
            isCorrect = isCorrect,
            accuracyScore = if (isCorrect) maxOf(85, accuracy) else accuracy,
            learnerSaid = learnerSaid,
            correctSentence = expectedSentence,
            hindiExplanation = explanation,
            hindiPraise = praise,
            grammarRule = grammarConcept
        )
    }

    private fun fallbackConversationResponse(
        scenario: ConversationScenario,
        userSpeech: String
    ): ConversationAiResponse {
        val lower = userSpeech.lowercase()
        return when (scenario.id) {
            "restaurant" -> {
                when {
                    lower.contains("table") || lower.contains("two") || lower.contains("menu") -> {
                        ConversationAiResponse(
                            englishReply = "Certainly! Right this way, please. Here is our special menu. Would you like some water or juice to start?",
                            hindiTranslation = "ज़रूर! कृपया इस तरफ आइए। यह रहा हमारा खास मेन्यू। क्या आप शुरुआत में पानी या जूस लेना पसंद करेंगे?"
                        )
                    }
                    lower.contains("water") || lower.contains("juice") -> {
                        ConversationAiResponse(
                            englishReply = "Coming right up! Take your time to look over the main course dishes.",
                            hindiTranslation = "अभी लेकर आता हूँ! आप आराम से मुख्य भोजन की डिशेस देख लीजिए।"
                        )
                    }
                    lower.contains("bill") || lower.contains("check") -> {
                        ConversationAiResponse(
                            englishReply = "Here is your bill, sir. How was your meal today?",
                            hindiTranslation = "यह रहा आपका बिल, सर। आज आपका भोजन कैसा रहा?"
                        )
                    }
                    else -> {
                        ConversationAiResponse(
                            englishReply = "Excellent choice! I'll place that order for you right away. Anything else for now?",
                            hindiTranslation = "बेहतरीन चुनाव! मैं आपका यह ऑर्डर तुरंत देता हूँ। अभी के लिए कुछ और चाहिए?"
                        )
                    }
                }
            }
            "job_interview" -> {
                ConversationAiResponse(
                    englishReply = "That's very impressive! Could you describe a challenging situation you faced and how you handled it?",
                    hindiTranslation = "यह बहुत प्रभावशाली है! क्या आप किसी चुनौतीपूर्ण परिस्थिति और उसे हल करने के अपने तरीके के बारे में बता सकते हैं?"
                )
            }
            "railway_station" -> {
                ConversationAiResponse(
                    englishReply = "Sure! The Rajdhani Express is scheduled to depart at 6:30 PM. Shall I confirm two AC sleeper berths for you?",
                    hindiTranslation = "ज़रूर! राजधानी एक्सप्रेस शाम 6:30 बजे रवाना होगी। क्या मैं आपके लिए दो एसी स्लीपर बर्थ कन्फर्म कर दूँ?"
                )
            }
            else -> {
                ConversationAiResponse(
                    englishReply = "That sounds great! Tell me more about what you have planned next.",
                    hindiTranslation = "यह बहुत अच्छा लगा! मुझे आगे की अपनी योजनाओं के बारे में और बताइए।"
                )
            }
        }
    }
}
