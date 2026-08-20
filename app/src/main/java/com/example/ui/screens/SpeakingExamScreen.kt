package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingExamScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isListening by viewModel.isMicListening.collectAsState()
    val amplitude by viewModel.micAmplitude.collectAsState()
    val liveText by viewModel.liveRecognizedText.collectAsState()

    val examPrompts = listOf(
        "1. Introduce yourself clearly in 3 sentences (Name, City, Profession).",
        "2. What did you do yesterday? (Speak in Past Tense).",
        "3. Order your favorite dinner at a restaurant.",
        "4. Why do you want to learn English fluently?",
        "5. Describe your dream job or goals for this year."
    )

    var currentPromptIndex by remember { mutableIntStateOf(0) }
    var isExamFinished by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "AI Voice Speaking Assessment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Fluency • Pronunciation • Grammar Scorecard",
                            style = MaterialTheme.typography.labelSmall,
                            color = LightAmber
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PureWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoyalBlue)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(14.dp))
                LinearProgressIndicator(
                    progress = { (currentPromptIndex + 1) / 5f },
                    color = SaffronOrange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            if (!isExamFinished) {
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = RoyalBlue.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "Question ${currentPromptIndex + 1} of 5",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalBlue,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = examPrompts[currentPromptIndex],
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            PulsingMicButton(
                                isListening = isListening,
                                amplitude = amplitude,
                                onClick = {
                                    if (isListening) viewModel.stopSpeakingRecording()
                                    else viewModel.startSpeakingRecording(examPrompts[currentPromptIndex], "Exam Prompt")
                                }
                            )

                            WaveformVisualizer(isListening = isListening, amplitude = amplitude)

                            if (liveText.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = "Captured: \"$liveText\"",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RoyalBlue,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (currentPromptIndex < 4) {
                                currentPromptIndex++
                            } else {
                                isExamFinished = true
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("exam_next_prompt_btn")
                    ) {
                        Text(
                            text = if (currentPromptIndex < 4) "अगला सवाल (Next Question) →" else "परीक्षा सबमिट करें (Submit Exam) 🏆",
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                }
            } else {
                // Exam Scorecard
                item {
                    ExamScorecardView(onFinish = onBack)
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ExamScorecardView(onFinish: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(WarmAmber.copy(alpha = 0.2f))
            ) {
                Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = "Trophy", tint = SaffronOrange, modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Speaking Assessment Result",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )

            Text(
                text = "Overall Band: B1 (Intermediate Fluent)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SaffronOrange,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    ScoreRowItem("🗣️ Fluency & Flow", "78%")
                    ScoreRowItem("📚 Grammar Accuracy", "82%")
                    ScoreRowItem("📖 Vocabulary Range", "75%")
                    ScoreRowItem("🔊 Pronunciation Clarity", "84%")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onFinish,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("डैशबोर्ड पर लौटें (Return Home)", color = PureWhite, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ScoreRowItem(title: String, score: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Text(text = score, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = RoyalBlue)
    }
}
