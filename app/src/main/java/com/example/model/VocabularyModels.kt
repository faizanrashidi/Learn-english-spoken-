package com.example.model

data class VocabularyWord(
    val id: String,
    val word: String,
    val hindiMeaning: String,
    val hindiPronunciation: String, // e.g. "वॉटर"
    val partOfSpeech: String, // Noun, Verb, Adj, Adv, Phrase
    val exampleSentence: String,
    val exampleSentenceHindi: String,
    val synonym: String? = null,
    val antonym: String? = null,
    val category: String, // Daily Verbs, Food, Travel, Emotions, Career, Idioms
    val levelCategory: String // "Beginner (Level 0-20)", "Intermediate (21-60)", "Advanced (61-100)"
)

object VocabularyDataRepository {
    val WORDS: List<VocabularyWord> = listOf(
        // Daily Basics (Level 0-20)
        VocabularyWord(
            id = "v1",
            word = "Water",
            hindiMeaning = "पानी / जल",
            hindiPronunciation = "वॉटर (Waa-ter)",
            partOfSpeech = "Noun",
            exampleSentence = "Please drink plenty of water every day.",
            exampleSentenceHindi = "कृपया रोज़ाना भरपूर पानी पिएं।",
            category = "Daily Basics",
            levelCategory = "Beginner (Level 0-20)"
        ),
        VocabularyWord(
            id = "v2",
            word = "Schedule",
            hindiMeaning = "समय सारिणी / कार्यक्रम",
            hindiPronunciation = "शेड्यूल / स्केड्यूल (Skeh-jool)",
            partOfSpeech = "Noun / Verb",
            exampleSentence = "What is your schedule for today?",
            exampleSentenceHindi = "आज का आपका कार्यक्रम क्या है?",
            synonym = "Timetable / Plan",
            category = "Daily Routine",
            levelCategory = "Beginner (Level 0-20)"
        ),
        VocabularyWord(
            id = "v3",
            word = "Opportunity",
            hindiMeaning = "अवसर / मौका",
            hindiPronunciation = "अपॉर्चुनिटी (Op-por-tu-ni-ty)",
            partOfSpeech = "Noun",
            exampleSentence = "This job is a wonderful opportunity for me.",
            exampleSentenceHindi = "यह नौकरी मेरे लिए एक शानदार मौका है।",
            synonym = "Chance / Opening",
            category = "Career",
            levelCategory = "Beginner (Level 0-20)"
        ),
        VocabularyWord(
            id = "v4",
            word = "Delicious",
            hindiMeaning = "स्वादिष्ट / लज़ीज़",
            hindiPronunciation = "डिलिशस (De-li-shus)",
            partOfSpeech = "Adjective",
            exampleSentence = "The food at the restaurant was delicious.",
            exampleSentenceHindi = "रेस्टोरेंट का खाना बहुत स्वादिष्ट था।",
            synonym = "Tasty / Yummy",
            antonym = "Tasteless / Bland",
            category = "Food & Dining",
            levelCategory = "Beginner (Level 0-20)"
        ),
        VocabularyWord(
            id = "v5",
            word = "Apologize",
            hindiMeaning = "माफी मांगना",
            hindiPronunciation = "अपोलोजाइज (Uh-pol-uh-jahyz)",
            partOfSpeech = "Verb",
            exampleSentence = "I apologize for being late to the meeting.",
            exampleSentenceHindi = "मीटिंग में देर से आने के लिए मैं माफी मांगता हूँ।",
            synonym = "Say sorry",
            category = "Polite English",
            levelCategory = "Beginner (Level 0-20)"
        ),
        VocabularyWord(
            id = "v6",
            word = "Comfortable",
            hindiMeaning = "आरामदायक / सहज",
            hindiPronunciation = "कम्फर्टेबल (Kum-fer-tuh-bul)",
            partOfSpeech = "Adjective",
            exampleSentence = "Are you comfortable in this chair?",
            exampleSentenceHindi = "क्या आप इस कुर्सी पर आराम से हैं?",
            synonym = "Cozy / Relaxed",
            antonym = "Uncomfortable",
            category = "Daily Basics",
            levelCategory = "Beginner (Level 0-20)"
        ),
        // Intermediate (Level 21-60)
        VocabularyWord(
            id = "v7",
            word = "Hesitate",
            hindiMeaning = "हिचकिचाना / संकोच करना",
            hindiPronunciation = "हेज़िटेट (Hez-i-tayt)",
            partOfSpeech = "Verb",
            exampleSentence = "Do not hesitate to ask questions in English.",
            exampleSentenceHindi = "अंग्रेजी में सवाल पूछने में बिल्कुल न हिचकिचाएं।",
            synonym = "Pause / Doubt",
            antonym = "Be confident",
            category = "Speaking Confidence",
            levelCategory = "Intermediate (Level 21-60)"
        ),
        VocabularyWord(
            id = "v8",
            word = "Fluency",
            hindiMeaning = "धाराप्रवाह बोलना / रवानी",
            hindiPronunciation = "फ्लूएंसी (Floo-un-see)",
            partOfSpeech = "Noun",
            exampleSentence = "Daily voice practice will bring fluency to your English.",
            exampleSentenceHindi = "रोजाना बोलकर अभ्यास करने से आपकी अंग्रेजी में प्रवाह आएगा।",
            synonym = "Smoothness / Eloquence",
            category = "Language Learning",
            levelCategory = "Intermediate (Level 21-60)"
        ),
        VocabularyWord(
            id = "v9",
            word = "Enthusiastic",
            hindiMeaning = "उत्साही / पुरजोश",
            hindiPronunciation = "एनथूजिएस्टिक (En-thoo-zee-as-tik)",
            partOfSpeech = "Adjective",
            exampleSentence = "She is very enthusiastic about learning new skills.",
            exampleSentenceHindi = "वह नए कौशल सीखने को लेकर बहुत उत्साही है।",
            synonym = "Passionate / Energetic",
            antonym = "Apathetic / Bored",
            category = "Emotions & Personality",
            levelCategory = "Intermediate (Level 21-60)"
        ),
        VocabularyWord(
            id = "v10",
            word = "Negotiate",
            hindiMeaning = "मोलभाव करना / समझौता वार्ता करना",
            hindiPronunciation = "नेगोशिएट (Neh-goh-shee-ayt)",
            partOfSpeech = "Verb",
            exampleSentence = "He negotiated a higher salary with the company.",
            exampleSentenceHindi = "उसने कंपनी के साथ अधिक वेतन के लिए बातचीत की।",
            synonym = "Bargain / Discuss terms",
            category = "Professional",
            levelCategory = "Intermediate (Level 21-60)"
        ),
        // Advanced (Level 61-100)
        VocabularyWord(
            id = "v11",
            word = "Articulate",
            hindiMeaning = "स्पष्ट और प्रभावी ढंग से बोलना",
            hindiPronunciation = "आर्टिक्यूलेट (Ahr-tik-yuh-lit)",
            partOfSpeech = "Adjective / Verb",
            exampleSentence = "She gave an articulate and persuasive speech.",
            exampleSentenceHindi = "उसने एक स्पष्ट और प्रभावशाली भाषण दिया।",
            synonym = "Expressive / Clear",
            antonym = "Inarticulate",
            category = "Advanced Speaking",
            levelCategory = "Advanced (Level 61-100)"
        ),
        VocabularyWord(
            id = "v12",
            word = "Piece of Cake",
            hindiMeaning = "बेहद आसान काम (Idiom / मुहावरा)",
            hindiPronunciation = "पीस ऑफ केक",
            partOfSpeech = "Idiom",
            exampleSentence = "Speaking English with practice is a piece of cake!",
            exampleSentenceHindi = "अभ्यास के साथ अंग्रेजी बोलना बच्चों का खेल (बहुत आसान) है!",
            synonym = "Very easy / Child's play",
            antonym = "Hard nut to crack",
            category = "Idioms & Expressions",
            levelCategory = "Advanced (Level 61-100)"
        ),
        VocabularyWord(
            id = "v13",
            word = "Once in a Blue Moon",
            hindiMeaning = "कभी-कभार / ईद का चाँद (Idiom)",
            hindiPronunciation = "वन्स इन अ ब्लू मून",
            partOfSpeech = "Idiom",
            exampleSentence = "He visits his hometown only once in a blue moon.",
            exampleSentenceHindi = "वह अपने गृहनगर केवल कभी-कभार ही जाता है।",
            synonym = "Rarely / Seldom",
            antonym = "Frequently / Often",
            category = "Idioms & Expressions",
            levelCategory = "Advanced (Level 61-100)"
        ),
        VocabularyWord(
            id = "v14",
            word = "Perseverance",
            hindiMeaning = "दृढ़ता / लगातार प्रयास",
            hindiPronunciation = "पर्सिवियरेंस (Pur-suh-veer-uns)",
            partOfSpeech = "Noun",
            exampleSentence = "Perseverance is the key to mastering any foreign language.",
            exampleSentenceHindi = "किसी भी विदेशी भाषा में महारत हासिल करने के लिए निरंतर प्रयास जरूरी है।",
            synonym = "Persistence / Dedication",
            category = "Mindset & Growth",
            levelCategory = "Advanced (Level 61-100)"
        )
    )
}
