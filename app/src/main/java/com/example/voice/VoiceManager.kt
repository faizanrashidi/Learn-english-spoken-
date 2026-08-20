package com.example.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class VoiceManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var isTtsInitialized = false

    private val _isSpeakingTts = MutableStateFlow(false)
    val isSpeakingTts: StateFlow<Boolean> = _isSpeakingTts.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _speechAmplitude = MutableStateFlow(0f)
    val speechAmplitude: StateFlow<Float> = _speechAmplitude.asStateFlow()

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    private var onSpeechResultCallback: ((String) -> Unit)? = null
    private var onSpeechErrorCallback: ((String) -> Unit)? = null

    init {
        initTts()
    }

    private fun initTts() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.let { engine ->
                    val result = engine.setLanguage(Locale("en", "IN"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        engine.language = Locale.US
                    }
                    engine.setPitch(1.0f)
                    engine.setSpeechRate(0.9f)
                    engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            _isSpeakingTts.value = true
                        }

                        override fun onDone(utteranceId: String?) {
                            _isSpeakingTts.value = false
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String?) {
                            _isSpeakingTts.value = false
                        }
                    })
                    isTtsInitialized = true
                }
            }
        }
    }

    fun speak(text: String, isSlow: Boolean = false, isHindi: Boolean = false) {
        if (!isTtsInitialized || tts == null) return
        stopTts()

        tts?.let { engine ->
            if (isHindi) {
                engine.language = Locale("hi", "IN")
            } else {
                val inResult = engine.setLanguage(Locale("en", "IN"))
                if (inResult == TextToSpeech.LANG_MISSING_DATA || inResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    engine.language = Locale.US
                }
            }
            engine.setSpeechRate(if (isSlow) 0.7f else 0.95f)
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance_${System.currentTimeMillis()}")
        }
    }

    fun stopTts() {
        tts?.stop()
        _isSpeakingTts.value = false
    }

    fun startListening(
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        stopTts()
        onSpeechResultCallback = onResult
        onSpeechErrorCallback = onError
        _recognizedText.value = ""

        try {
            if (SpeechRecognizer.isRecognitionAvailable(context)) {
                speechRecognizer?.destroy()
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                    setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(params: Bundle?) {
                            _isListening.value = true
                        }

                        override fun onBeginningOfSpeech() {
                            _isListening.value = true
                        }

                        override fun onRmsChanged(rmsdB: Float) {
                            // Normalize 0 to 1
                            val normalized = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
                            _speechAmplitude.value = normalized
                        }

                        override fun onBufferReceived(buffer: ByteArray?) {}

                        override fun onEndOfSpeech() {
                            _isListening.value = false
                            _speechAmplitude.value = 0f
                        }

                        override fun onError(error: Int) {
                            _isListening.value = false
                            _speechAmplitude.value = 0f
                            val errorMsg = when (error) {
                                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                                SpeechRecognizer.ERROR_CLIENT -> "Client error"
                                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required"
                                SpeechRecognizer.ERROR_NETWORK, SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network issue"
                                SpeechRecognizer.ERROR_NO_MATCH -> "Could not understand speech"
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Speech recognizer busy"
                                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech heard"
                                else -> "Speech recognition error"
                            }
                            onSpeechErrorCallback?.invoke(errorMsg)
                        }

                        override fun onResults(results: Bundle?) {
                            _isListening.value = false
                            _speechAmplitude.value = 0f
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val bestSpoken = matches?.firstOrNull() ?: ""
                            _recognizedText.value = bestSpoken
                            onSpeechResultCallback?.invoke(bestSpoken)
                        }

                        override fun onPartialResults(partialResults: Bundle?) {
                            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val partial = matches?.firstOrNull() ?: ""
                            _recognizedText.value = partial
                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {}
                    })
                }

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
                    putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                    putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
                }

                speechRecognizer?.startListening(intent)
            } else {
                onError("Speech recognition not available on this device")
            }
        } catch (e: Exception) {
            _isListening.value = false
            onError(e.message ?: "Failed to start speech recognition")
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (_: Exception) {}
        _isListening.value = false
        _speechAmplitude.value = 0f
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            speechRecognizer?.destroy()
        } catch (_: Exception) {}
    }
}
