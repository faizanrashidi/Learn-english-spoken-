package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.ConversationAiResponse
import com.example.ai.GeminiAiService
import com.example.ai.PostChatReport
import com.example.ai.SpokenSentenceEvaluation
import com.example.data.local.AppDatabase
import com.example.data.local.MistakeEntity
import com.example.data.local.UserProgressEntity
import com.example.data.repository.EnglishLearningRepository
import com.example.model.ConversationDataRepository
import com.example.model.ConversationMessage
import com.example.model.ConversationScenario
import com.example.model.GrammarCatalogData
import com.example.model.GrammarPracticeQuestion
import com.example.model.GrammarTopic
import com.example.model.LevelDataRepository
import com.example.model.LevelItem
import com.example.model.MessageSender
import com.example.model.VocabularyDataRepository
import com.example.model.VocabularyWord
import com.example.voice.VoiceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class LessonStep {
    CONCEPT_INTRO,
    LISTEN_PRONUNCIATION,
    HINDI_MEANING,
    EXAMPLES,
    PRACTICE_READING,
    SPEAK_NOW_RECORD,
    AI_EVALUATION_FEEDBACK,
    REPEAT_DRILL,
    CONVERSATION_ROLEPLAY,
    MINI_QUIZ,
    LESSON_COMPLETE
}

class LearningViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EnglishLearningRepository(AppDatabase.getInstance(application))
    private val voiceManager = VoiceManager(application)
    private val aiService = GeminiAiService()

    val userProgress: StateFlow<UserProgressEntity> = repository.userProgressFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProgressEntity()
    )

    val mistakeList: StateFlow<List<MistakeEntity>> = repository.allMistakesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Voice Engine State
    val isTtsSpeaking = voiceManager.isSpeakingTts
    val isMicListening = voiceManager.isListening
    val micAmplitude = voiceManager.speechAmplitude
    val liveRecognizedText = voiceManager.recognizedText

    // Navigation & Selected States
    private val _selectedLevel = MutableStateFlow(LevelDataRepository.ALL_LEVELS.first())
    val selectedLevel: StateFlow<LevelItem> = _selectedLevel.asStateFlow()

    private val _currentLessonStep = MutableStateFlow(LessonStep.CONCEPT_INTRO)
    val currentLessonStep: StateFlow<LessonStep> = _currentLessonStep.asStateFlow()

    private val _currentSentenceIndex = MutableStateFlow(0)
    val currentSentenceIndex: StateFlow<Int> = _currentSentenceIndex.asStateFlow()

    private val _speechEvaluation = MutableStateFlow<SpokenSentenceEvaluation?>(null)
    val speechEvaluation: StateFlow<SpokenSentenceEvaluation?> = _speechEvaluation.asStateFlow()

    private val _isEvaluating = MutableStateFlow(false)
    val isEvaluating: StateFlow<Boolean> = _isEvaluating.asStateFlow()

    // Lesson Quiz State
    private val _lessonQuizScore = MutableStateFlow(0)
    val lessonQuizScore: StateFlow<Int> = _lessonQuizScore.asStateFlow()

    private val _lessonQuizIndex = MutableStateFlow(0)
    val lessonQuizIndex: StateFlow<Int> = _lessonQuizIndex.asStateFlow()

    // Grammar Catalog State
    private val _selectedGrammarTopic = MutableStateFlow(GrammarCatalogData.ALL_TOPICS.first())
    val selectedGrammarTopic: StateFlow<GrammarTopic> = _selectedGrammarTopic.asStateFlow()

    private val _grammarSearchQuery = MutableStateFlow("")
    val grammarSearchQuery: StateFlow<String> = _grammarSearchQuery.asStateFlow()

    // AI Talk & Conversation Room State
    private val _activeScenario = MutableStateFlow(ConversationDataRepository.SCENARIOS.first())
    val activeScenario: StateFlow<ConversationScenario> = _activeScenario.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ConversationMessage>>(emptyList())
    val chatMessages: StateFlow<List<ConversationMessage>> = _chatMessages.asStateFlow()

    private val _showHindiSubtitles = MutableStateFlow(true)
    val showHindiSubtitles: StateFlow<Boolean> = _showHindiSubtitles.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    private val _postChatReport = MutableStateFlow<PostChatReport?>(null)
    val postChatReport: StateFlow<PostChatReport?> = _postChatReport.asStateFlow()

    // Vocabulary Search & Filter
    private val _vocabFilter = MutableStateFlow("All")
    val vocabFilter: StateFlow<String> = _vocabFilter.asStateFlow()

    // UI Toast Message
    private val _uiToast = MutableStateFlow<String?>(null)
    val uiToast: StateFlow<String?> = _uiToast.asStateFlow()

    init {
        initScenarioChat(ConversationDataRepository.SCENARIOS.first())
    }

    // -------------------------------------------------------------
    // LESSON FLOW METHODS
    // -------------------------------------------------------------
    fun selectLevel(level: LevelItem) {
        _selectedLevel.value = level
        _currentLessonStep.value = LessonStep.CONCEPT_INTRO
        _currentSentenceIndex.value = 0
        _speechEvaluation.value = null
        _lessonQuizScore.value = 0
        _lessonQuizIndex.value = 0
    }

    fun nextLessonStep() {
        val next = when (_currentLessonStep.value) {
            LessonStep.CONCEPT_INTRO -> LessonStep.LISTEN_PRONUNCIATION
            LessonStep.LISTEN_PRONUNCIATION -> LessonStep.HINDI_MEANING
            LessonStep.HINDI_MEANING -> LessonStep.EXAMPLES
            LessonStep.EXAMPLES -> LessonStep.PRACTICE_READING
            LessonStep.PRACTICE_READING -> LessonStep.SPEAK_NOW_RECORD
            LessonStep.SPEAK_NOW_RECORD -> LessonStep.AI_EVALUATION_FEEDBACK
            LessonStep.AI_EVALUATION_FEEDBACK -> {
                val eval = _speechEvaluation.value
                if (eval != null && !eval.isCorrect) {
                    LessonStep.REPEAT_DRILL
                } else {
                    LessonStep.CONVERSATION_ROLEPLAY
                }
            }
            LessonStep.REPEAT_DRILL -> LessonStep.SPEAK_NOW_RECORD
            LessonStep.CONVERSATION_ROLEPLAY -> LessonStep.MINI_QUIZ
            LessonStep.MINI_QUIZ -> {
                // Complete lesson & unlock next
                viewModelScope.launch {
                    repository.unlockNextLevel(_selectedLevel.value.levelNumber)
                    repository.addXpAndSpeakingTime(150, 180)
                }
                LessonStep.LESSON_COMPLETE
            }
            LessonStep.LESSON_COMPLETE -> LessonStep.CONCEPT_INTRO
        }
        _currentLessonStep.value = next
    }

    fun previousLessonStep() {
        val prev = when (_currentLessonStep.value) {
            LessonStep.CONCEPT_INTRO -> LessonStep.CONCEPT_INTRO
            LessonStep.LISTEN_PRONUNCIATION -> LessonStep.CONCEPT_INTRO
            LessonStep.HINDI_MEANING -> LessonStep.LISTEN_PRONUNCIATION
            LessonStep.EXAMPLES -> LessonStep.HINDI_MEANING
            LessonStep.PRACTICE_READING -> LessonStep.EXAMPLES
            LessonStep.SPEAK_NOW_RECORD -> LessonStep.PRACTICE_READING
            LessonStep.AI_EVALUATION_FEEDBACK -> LessonStep.SPEAK_NOW_RECORD
            LessonStep.REPEAT_DRILL -> LessonStep.AI_EVALUATION_FEEDBACK
            LessonStep.CONVERSATION_ROLEPLAY -> LessonStep.AI_EVALUATION_FEEDBACK
            LessonStep.MINI_QUIZ -> LessonStep.CONVERSATION_ROLEPLAY
            LessonStep.LESSON_COMPLETE -> LessonStep.MINI_QUIZ
        }
        _currentLessonStep.value = prev
    }

    fun startSpeakingRecording(targetSentence: String, grammarTopic: String) {
        voiceManager.startListening(
            onResult = { spokenText ->
                evaluateUserSpeech(spokenText, targetSentence, grammarTopic)
            },
            onError = { errMsg ->
                _uiToast.value = "माइक संदेश: $errMsg"
            }
        )
    }

    fun stopSpeakingRecording() {
        voiceManager.stopListening()
    }

    fun evaluateUserSpeech(spokenText: String, expectedSentence: String, grammarTopic: String) {
        viewModelScope.launch {
            _isEvaluating.value = true
            val eval = aiService.evaluateSpeech(
                learnerSaid = spokenText,
                expectedSentence = expectedSentence,
                grammarConcept = grammarTopic,
                learnerLevel = _selectedLevel.value.levelNumber
            )
            _speechEvaluation.value = eval
            _isEvaluating.value = false
            _currentLessonStep.value = LessonStep.AI_EVALUATION_FEEDBACK

            // Record mistake if incorrect
            if (!eval.isCorrect && spokenText.isNotBlank()) {
                repository.recordMistake(
                    learnerSaid = spokenText,
                    correctSentence = expectedSentence,
                    hindiExplanation = eval.hindiExplanation,
                    grammarTopic = grammarTopic
                )
            }

            // Award XP for speaking attempt
            val xp = if (eval.isCorrect) 50 else 20
            repository.addXpAndSpeakingTime(xp, 30)

            // Auto-speak feedback audio
            if (eval.isCorrect) {
                voiceManager.speak("Excellent! That is correct.")
            } else {
                voiceManager.speak("The correct sentence is: $expectedSentence", isSlow = true)
            }
        }
    }

    // -------------------------------------------------------------
    // VOICE AUDIO PLAYBACK
    // -------------------------------------------------------------
    fun speakEnglish(text: String, isSlow: Boolean = false) {
        voiceManager.speak(text, isSlow = isSlow, isHindi = false)
    }

    fun speakHindi(text: String) {
        voiceManager.speak(text, isSlow = false, isHindi = true)
    }

    fun stopAudio() {
        voiceManager.stopTts()
    }

    // -------------------------------------------------------------
    // GRAMMAR CATALOG ACTIONS
    // -------------------------------------------------------------
    fun selectGrammarTopic(topic: GrammarTopic) {
        _selectedGrammarTopic.value = topic
    }

    fun setGrammarSearchQuery(query: String) {
        _grammarSearchQuery.value = query
    }

    // -------------------------------------------------------------
    // TALK WITH AI & CONVERSATION ROOM
    // -------------------------------------------------------------
    fun selectScenario(scenario: ConversationScenario) {
        _activeScenario.value = scenario
        initScenarioChat(scenario)
    }

    private fun initScenarioChat(scenario: ConversationScenario) {
        _chatMessages.value = listOf(
            ConversationMessage(
                id = "init_ai_msg",
                sender = MessageSender.AI_TEACHER,
                textEnglish = scenario.initialAiMessageEnglish,
                textHindi = scenario.initialAiMessageHindi
            )
        )
        _postChatReport.value = null
    }

    fun toggleHindiSubtitles() {
        _showHindiSubtitles.value = !_showHindiSubtitles.value
    }

    fun sendLearnerVoiceMessage(learnerText: String) {
        if (learnerText.isBlank()) return

        val userMessage = ConversationMessage(
            id = "user_${System.currentTimeMillis()}",
            sender = MessageSender.LEARNER,
            textEnglish = learnerText
        )

        _chatMessages.value = _chatMessages.value + userMessage

        viewModelScope.launch {
            _isAiThinking.value = true
            val aiResponse = aiService.generateChatReply(
                scenario = _activeScenario.value,
                conversationHistory = _chatMessages.value,
                latestUserSpeech = learnerText,
                learnerLevel = userProgress.value.currentLevel
            )

            val aiMessage = ConversationMessage(
                id = "ai_${System.currentTimeMillis()}",
                sender = MessageSender.AI_TEACHER,
                textEnglish = aiResponse.englishReply,
                textHindi = aiResponse.hindiTranslation,
                grammarFeedback = aiResponse.grammarFeedback,
                betterAlternative = aiResponse.betterSentence
            )

            _chatMessages.value = _chatMessages.value + aiMessage
            _isAiThinking.value = false

            // Voice speak the AI reply automatically!
            voiceManager.speak(aiResponse.englishReply)

            // Reward speaking XP
            repository.addXpAndSpeakingTime(35, 45)
        }
    }

    fun startChatMicRecording() {
        voiceManager.startListening(
            onResult = { spokenText ->
                sendLearnerVoiceMessage(spokenText)
            },
            onError = { err ->
                _uiToast.value = "आवाज नहीं सुनाई दी: $err"
            }
        )
    }

    fun finishConversationAndGenerateReport() {
        viewModelScope.launch {
            _isAiThinking.value = true
            val report = aiService.generatePostChatReport(
                messages = _chatMessages.value,
                scenario = _activeScenario.value
            )
            _postChatReport.value = report
            _isAiThinking.value = false
            repository.addXpAndSpeakingTime(100, 60)
        }
    }

    // -------------------------------------------------------------
    // MISTAKE MEMORY ACTIONS
    // -------------------------------------------------------------
    fun deleteMistake(id: Long) {
        viewModelScope.launch {
            repository.deleteMistake(id)
        }
    }

    fun clearUiToast() {
        _uiToast.value = null
    }

    fun setVocabFilter(filter: String) {
        _vocabFilter.value = filter
    }

    fun updateUserGoal(goal: String) {
        viewModelScope.launch {
            repository.updateGoal(goal)
        }
    }

    fun toggleProSubscription(isPro: Boolean) {
        viewModelScope.launch {
            repository.togglePremium(isPro)
        }
    }

    override fun onCleared() {
        super.onCleared()
        voiceManager.shutdown()
    }
}
