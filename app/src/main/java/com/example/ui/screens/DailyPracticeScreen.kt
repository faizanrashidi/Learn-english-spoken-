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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun DailyPracticeScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    onNavigateToTalkAi: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isListening by viewModel.isMicListening.collectAsState()
    val amplitude by viewModel.micAmplitude.collectAsState()
    val liveText by viewModel.liveRecognizedText.collectAsState()

    val dailyTasks = listOf(
        DailyWorkoutTask("1. ग्रामर ड्रिल (Grammar)", "10 MCQs on Tenses & Modals", true, "10/10 Done"),
        DailyWorkoutTask("2. नया शब्दकोश (Vocab)", "5 New Words with Hindi Pronunciation", true, "5/5 Learned"),
        DailyWorkoutTask("3. अनुवाद (Translation)", "Hindi to English Instant Translate", false, "Pending"),
        DailyWorkoutTask("4. बोलकर अभ्यास (Voice Drill)", "5 Sentences with AI Microphone", false, "Pending"),
        DailyWorkoutTask("5. AI बातचीत (Roleplay)", "1 Full Session with Priya Ma'am", false, "Pending")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Daily 10-Min Workout",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "10 मिनट रोज = 30 दिन में आत्मविश्वास!",
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
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                DailyStreakHeaderCard()
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                Text(
                    text = "आज का 10-मिनट रूटीन (Today's Routine)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(dailyTasks.size) { index ->
                val task = dailyTasks[index]
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (task.isDone) SuccessGreen.copy(alpha = 0.15f) else RoyalBlue.copy(alpha = 0.12f))
                            ) {
                                Icon(
                                    imageVector = if (task.isDone) Icons.Default.CheckCircle else Icons.Default.FitnessCenter,
                                    contentDescription = null,
                                    tint = if (task.isDone) SuccessGreen else RoyalBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = task.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (task.isDone) SuccessGreen.copy(alpha = 0.12f) else WarmAmber.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = task.statusText,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (task.isDone) SuccessGreen else SaffronOrange,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Quick Voice Speaking Practice in Daily Workout
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "🎤 Quick Voice Challenge:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "\"I practice speaking English every single day.\"",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PulsingMicButton(
                            isListening = isListening,
                            amplitude = amplitude,
                            onClick = {
                                if (isListening) viewModel.stopSpeakingRecording()
                                else viewModel.startSpeakingRecording("I practice speaking English every single day.", "Daily Routine")
                            }
                        )

                        WaveformVisualizer(isListening = isListening, amplitude = amplitude)

                        if (liveText.isNotBlank()) {
                            Text(
                                text = "सुना: \"$liveText\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = SaffronOrange,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToTalkAi,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("daily_practice_talk_ai_button")
                ) {
                    Text(
                        text = "🎤 Start 1-on-1 AI Conversation (Final Step)",
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

data class DailyWorkoutTask(
    val title: String,
    val subtitle: String,
    val isDone: Boolean,
    val statusText: String
)

@Composable
fun DailyStreakHeaderCard() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SaffronOrange),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "🔥 4 Day Streak!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
                Text(
                    text = "आज का अभ्यास पूरा करके स्ट्रीक बचाएं",
                    style = MaterialTheme.typography.bodySmall,
                    color = LightAmber
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = PureWhite
            ) {
                Text(
                    text = "🎯 +200 XP",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = SaffronOrange,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}
