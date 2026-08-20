package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FeedbackCard
import com.example.ui.components.PronunciationAudioButtons
import com.example.ui.components.PulsingMicButton
import com.example.ui.components.WaveformVisualizer
import com.example.ui.theme.LightAmber
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmAmber
import com.example.ui.viewmodel.LearningViewModel
import com.example.ui.viewmodel.LessonStep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonInteractiveScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedLevel by viewModel.selectedLevel.collectAsState()
    val currentStep by viewModel.currentLessonStep.collectAsState()
    val speechEvaluation by viewModel.speechEvaluation.collectAsState()
    val isEvaluating by viewModel.isEvaluating.collectAsState()
    val isListening by viewModel.isMicListening.collectAsState()
    val amplitude by viewModel.micAmplitude.collectAsState()
    val liveText by viewModel.liveRecognizedText.collectAsState()

    val targetSentence = selectedLevel.targetSentenceExamples.firstOrNull() ?: "Hello, how are you?"

    val stepProgress = when (currentStep) {
        LessonStep.CONCEPT_INTRO -> 0.1f
        LessonStep.LISTEN_PRONUNCIATION -> 0.2f
        LessonStep.HINDI_MEANING -> 0.35f
        LessonStep.EXAMPLES -> 0.5f
        LessonStep.PRACTICE_READING -> 0.65f
        LessonStep.SPEAK_NOW_RECORD -> 0.75f
        LessonStep.AI_EVALUATION_FEEDBACK -> 0.85f
        LessonStep.REPEAT_DRILL -> 0.8f
        LessonStep.CONVERSATION_ROLEPLAY -> 0.92f
        LessonStep.MINI_QUIZ -> 0.97f
        LessonStep.LESSON_COMPLETE -> 1.0f
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Level ${selectedLevel.levelNumber}: ${selectedLevel.title}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = selectedLevel.hindiTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = LightAmber
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PureWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoyalBlue)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            // Lesson Step Progress Bar
            LinearProgressIndicator(
                progress = { stepProgress },
                color = SaffronOrange,
                trackColor = RoyalBlue.copy(alpha = 0.15f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )

            // Step Indicator Pill
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = RoyalBlue.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = getStepLabel(currentStep),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBlue,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "🎯 Goal: ${selectedLevel.targetSpeakingGoal}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                when (currentStep) {
                    LessonStep.CONCEPT_INTRO -> {
                        ConceptIntroView(
                            level = selectedLevel,
                            onListen = { viewModel.speakEnglish(targetSentence) }
                        )
                    }
                    LessonStep.LISTEN_PRONUNCIATION -> {
                        ListenPronunciationView(
                            targetSentence = targetSentence,
                            onPlayNormal = { viewModel.speakEnglish(targetSentence, isSlow = false) },
                            onPlaySlow = { viewModel.speakEnglish(targetSentence, isSlow = true) }
                        )
                    }
                    LessonStep.HINDI_MEANING -> {
                        HindiMeaningView(
                            level = selectedLevel,
                            targetSentence = targetSentence,
                            onPlayHindi = { viewModel.speakHindi(selectedLevel.hindiTitle) }
                        )
                    }
                    LessonStep.EXAMPLES -> {
                        ExamplesView(
                            level = selectedLevel,
                            onSpeakExample = { viewModel.speakEnglish(it) }
                        )
                    }
                    LessonStep.PRACTICE_READING -> {
                        PracticeReadingView(
                            targetSentence = targetSentence,
                            onListen = { viewModel.speakEnglish(targetSentence) }
                        )
                    }
                    LessonStep.SPEAK_NOW_RECORD -> {
                        SpeakNowRecordView(
                            targetSentence = targetSentence,
                            grammarTopic = selectedLevel.targetGrammarConcept,
                            isListening = isListening,
                            amplitude = amplitude,
                            liveText = liveText,
                            isEvaluating = isEvaluating,
                            onStartRecording = {
                                viewModel.startSpeakingRecording(targetSentence, selectedLevel.targetGrammarConcept)
                            },
                            onStopRecording = { viewModel.stopSpeakingRecording() },
                            onListenExpected = { viewModel.speakEnglish(targetSentence) }
                        )
                    }
                    LessonStep.AI_EVALUATION_FEEDBACK -> {
                        speechEvaluation?.let { eval ->
                            FeedbackCard(
                                isCorrect = eval.isCorrect,
                                accuracyScore = eval.accuracyScore,
                                learnerSaid = eval.learnerSaid,
                                correctSentence = eval.correctSentence,
                                hindiExplanation = eval.hindiExplanation,
                                hindiPraise = eval.hindiPraise,
                                onSpeakAgain = {
                                    viewModel.startSpeakingRecording(targetSentence, selectedLevel.targetGrammarConcept)
                                },
                                onContinue = { viewModel.nextLessonStep() }
                            )
                        } ?: run {
                            Text("मूल्यांकन हो रहा है...", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    LessonStep.REPEAT_DRILL -> {
                        RepeatDrillView(
                            targetSentence = targetSentence,
                            onListen = { viewModel.speakEnglish(targetSentence, isSlow = true) },
                            onSpeakAgain = { viewModel.nextLessonStep() }
                        )
                    }
                    LessonStep.CONVERSATION_ROLEPLAY -> {
                        ConversationRoleplayView(
                            targetSentence = targetSentence,
                            onListenAi = { viewModel.speakEnglish("Hi! How are you doing today?") }
                        )
                    }
                    LessonStep.MINI_QUIZ -> {
                        MiniQuizView(
                            targetSentence = targetSentence,
                            onCorrectAnswer = { viewModel.nextLessonStep() }
                        )
                    }
                    LessonStep.LESSON_COMPLETE -> {
                        LessonCompleteView(
                            level = selectedLevel,
                            onFinish = onBack
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Bottom Navigation Actions
            if (currentStep != LessonStep.LESSON_COMPLETE) {
                Surface(
                    color = PureWhite,
                    tonalElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentStep != LessonStep.CONCEPT_INTRO) {
                            OutlinedButton(
                                onClick = { viewModel.previousLessonStep() },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("← पीछे (Back)")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        Button(
                            onClick = { viewModel.nextLessonStep() },
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(if (currentStep == LessonStep.CONCEPT_INTRO) 2f else 1f)
                                .testTag("lesson_next_step_button")
                        ) {
                            Text(
                                text = if (currentStep == LessonStep.AI_EVALUATION_FEEDBACK) "आगे बढ़ें →" else "अगला कदम (Next) →",
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getStepLabel(step: LessonStep): String {
    return when (step) {
        LessonStep.CONCEPT_INTRO -> "1. नियम समझें (Concept)"
        LessonStep.LISTEN_PRONUNCIATION -> "2. उच्चारण सुनें (Listen)"
        LessonStep.HINDI_MEANING -> "3. हिंदी अर्थ (Hindi Meaning)"
        LessonStep.EXAMPLES -> "4. उदाहरण (Examples)"
        LessonStep.PRACTICE_READING -> "5. बोलकर पढ़ें (Practice)"
        LessonStep.SPEAK_NOW_RECORD -> "6. 🎤 बोलकर दिखाएं (Speak Now)"
        LessonStep.AI_EVALUATION_FEEDBACK -> "7. 💡 AI सुधार (AI Correction)"
        LessonStep.REPEAT_DRILL -> "8. दोहराएं (Repeat Drill)"
        LessonStep.CONVERSATION_ROLEPLAY -> "9. बातचीत (Conversation)"
        LessonStep.MINI_QUIZ -> "10. छोटा टेस्ट (Mini Quiz)"
        LessonStep.LESSON_COMPLETE -> "11. पूरा हुआ! (Completed)"
    }
}

@Composable
fun ConceptIntroView(level: com.example.model.LevelItem, onListen: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "📚 आज का मुख्य नियम (Concept):",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = level.summary,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = LightAmber,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "💡 बोलने में क्यों जरूरी है?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SaffronOrange
                    )
                    Text(
                        text = "जब तक आप इस नियम को बोलकर नहीं दोहराएंगे, जुबान पर अंग्रेजी नहीं चढ़ेगी। आज हम इसे 5 बार बुलवाएंगे!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ListenPronunciationView(
    targetSentence: String,
    onPlayNormal: () -> Unit,
    onPlaySlow: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🎧 ध्यान से उच्चारण सुनें:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = RoyalBlue.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"$targetSentence\"",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            PronunciationAudioButtons(
                onPlayNormal = onPlayNormal,
                onPlaySlow = onPlaySlow
            )
        }
    }
}

@Composable
fun HindiMeaningView(
    level: com.example.model.LevelItem,
    targetSentence: String,
    onPlayHindi: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "🇮🇳 हिंदी में अर्थ और उपयोग:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SaffronOrange
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "English:",
                        style = MaterialTheme.typography.labelSmall,
                        color = RoyalBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = targetSentence,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "हिंदी अर्थ:",
                        style = MaterialTheme.typography.labelSmall,
                        color = SaffronOrange,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = level.hindiTitle,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ExamplesView(
    level: com.example.model.LevelItem,
    onSpeakExample: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "✨ और उदाहरण (Listen & Observe):",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = RoyalBlue,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        level.targetSentenceExamples.forEachIndexed { index, example ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${index + 1}. $example",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = { onSpeakExample(example) }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Listen",
                            tint = RoyalBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PracticeReadingView(
    targetSentence: String,
    onListen: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "📢 जोर से 3 बार बोलकर पढ़ें:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SaffronOrange.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"$targetSentence\"",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SaffronOrange,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onListen,
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("एक बार फिर सुनें (Listen Again)")
            }
        }
    }
}

@Composable
fun SpeakNowRecordView(
    targetSentence: String,
    grammarTopic: String,
    isListening: Boolean,
    amplitude: Float,
    liveText: String,
    isEvaluating: Boolean,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onListenExpected: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🎤 अब आपकी बारी है (Your Turn to Speak!)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SaffronOrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "नीचे दिया गया वाक्य माइक दबाकर बोलें:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = RoyalBlue.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"$targetSentence\"",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isEvaluating) {
                CircularProgressIndicator(color = SaffronOrange)
                Spacer(modifier = Modifier.height(12.dp))
                Text("AI गुरुजी आपकी आवाज परख रहे हैं...", style = MaterialTheme.typography.bodyMedium)
            } else {
                PulsingMicButton(
                    isListening = isListening,
                    amplitude = amplitude,
                    onClick = {
                        if (isListening) onStopRecording() else onStartRecording()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                WaveformVisualizer(isListening = isListening, amplitude = amplitude)

                if (liveText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "सुनाई दिया: \"$liveText\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = RoyalBlue,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RepeatDrillView(
    targetSentence: String,
    onListen: () -> Unit,
    onSpeakAgain: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🔄 दोबारा अभ्यास (Repeat Drill):",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SaffronOrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "सही वाक्य को कान में बैठाएं और दोबारा बोलें:",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = RoyalBlue.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"$targetSentence\"",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onListen,
                colors = ButtonDefaults.buttonColors(containerColor = WarmAmber),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = PureWhite)
                Spacer(modifier = Modifier.width(6.dp))
                Text("धीमी आवाज में सुनें (Listen Slow)", color = PureWhite)
            }
        }
    }
}

@Composable
fun ConversationRoleplayView(
    targetSentence: String,
    onListenAi: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "💬 रियल लाइफ बातचीत में उपयोग:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(14.dp))

            // AI prompt bubble
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = RoyalBlue.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🤖 AI:", fontWeight = FontWeight.Bold, color = RoyalBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hello! Nice to meet you. How are you today?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onListenAi) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = RoyalBlue)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // User response
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SaffronOrange.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🗣️ You:", fontWeight = FontWeight.Bold, color = SaffronOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = targetSentence,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SaffronOrange
                    )
                }
            }
        }
    }
}

@Composable
fun MiniQuizView(
    targetSentence: String,
    onCorrectAnswer: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "📝 छोटा टेस्ट (Mini Quiz Check):",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "सही वाक्य चुनें (Select the grammatically correct sentence):",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Option 1 (Correct)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = RoyalBlue.copy(alpha = 0.08f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCorrectAnswer() }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("A.", fontWeight = FontWeight.Bold, color = RoyalBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = targetSentence,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Option 2 (Incorrect)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("B.", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I goes to market yesterday.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun LessonCompleteView(
    level: com.example.model.LevelItem,
    onFinish: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(28.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(SuccessGreen.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = SuccessGreen,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "बधाई हो! पाठ पूरा हुआ! 🎉",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue,
                textAlign = TextAlign.Center
            )

            Text(
                text = "आपने Level ${level.levelNumber} सफलता से पास कर लिया!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = WarmAmber.copy(alpha = 0.15f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text("⚡ +150", fontWeight = FontWeight.Bold, color = SaffronOrange)
                        Text("XP Earned", style = MaterialTheme.typography.labelSmall)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SuccessGreen.copy(alpha = 0.15f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text("🎯 95%", fontWeight = FontWeight.Bold, color = SuccessGreen)
                        Text("Accuracy", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onFinish,
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("lesson_finish_button")
            ) {
                Text(
                    text = "होम पर वापस जाएं (Back to Home)",
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }
        }
    }
}
