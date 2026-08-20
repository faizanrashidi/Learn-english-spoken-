package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.PostChatReport
import com.example.model.ConversationDataRepository
import com.example.model.ConversationMessage
import com.example.model.ConversationScenario
import com.example.model.MessageSender
import com.example.ui.components.PulsingMicButton
import com.example.ui.components.WaveformVisualizer
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.ErrorRed
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
fun TalkWithAiScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeScenario by viewModel.activeScenario.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val showHindiSubtitles by viewModel.showHindiSubtitles.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    val isListening by viewModel.isMicListening.collectAsState()
    val amplitude by viewModel.micAmplitude.collectAsState()
    val liveText by viewModel.liveRecognizedText.collectAsState()
    val postReport by viewModel.postChatReport.collectAsState()

    var typedMessage by remember { mutableStateOf("") }
    var showScenarioPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = activeScenario.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite,
                                maxLines = 1
                            )
                        }
                        Text(
                            text = "AI: ${activeScenario.aiRole} • You: ${activeScenario.userRole}",
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
                actions = {
                    IconButton(onClick = { viewModel.toggleHindiSubtitles() }) {
                        Icon(
                            imageVector = Icons.Default.Subtitles,
                            contentDescription = "Subtitles",
                            tint = if (showHindiSubtitles) WarmAmber else PureWhite.copy(alpha = 0.6f)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite.copy(alpha = 0.15f),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { showScenarioPicker = true }
                    ) {
                        Text(
                            text = "🔄 Scenarios",
                            style = MaterialTheme.typography.labelSmall,
                            color = PureWhite,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
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
            // Starter Prompts Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                items(activeScenario.starterPrompts) { prompt ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        tonalElevation = 2.dp,
                        modifier = Modifier.clickable {
                            viewModel.sendLearnerVoiceMessage(prompt)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("💡 ", fontSize = 12.sp)
                            Text(
                                text = prompt,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = RoyalBlue
                            )
                        }
                    }
                }
            }

            // Chat Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(chatMessages) { message ->
                    ChatBubbleItem(
                        message = message,
                        showHindi = showHindiSubtitles,
                        onSpeakText = { viewModel.speakEnglish(it) }
                    )
                }

                if (isThinking) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp),
                                color = SaffronOrange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${activeScenario.aiRole} सोच रहे हैं...",
                                style = MaterialTheme.typography.labelSmall,
                                color = SaffronOrange
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Finish & Generate Report CTA Bar
            if (chatMessages.size >= 3) {
                Surface(
                    color = LightAmber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.finishConversationAndGenerateReport() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.Assessment, contentDescription = "Report", tint = SaffronOrange)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "बातचीत समाप्त करें और AI रिपोर्ट देखें (View Report)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                    }
                }
            }

            // Live Voice waveform / input controls
            Surface(
                color = PureWhite,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp)
                ) {
                    WaveformVisualizer(isListening = isListening, amplitude = amplitude)

                    if (isListening && liveText.isNotBlank()) {
                        Text(
                            text = "सुनाई दिया: \"$liveText\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = typedMessage,
                            onValueChange = { typedMessage = it },
                            placeholder = { Text("यहाँ लिखें या माइक से बोलें...") },
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .testTag("chat_text_input"),
                            trailingIcon = {
                                if (typedMessage.isNotBlank()) {
                                    IconButton(onClick = {
                                        viewModel.sendLearnerVoiceMessage(typedMessage)
                                        typedMessage = ""
                                    }) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = RoyalBlue)
                                    }
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Mic Button
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(if (isListening) SaffronOrange else RoyalBlue)
                                .clickable {
                                    if (isListening) viewModel.stopAudio()
                                    else viewModel.startChatMicRecording()
                                }
                                .testTag("chat_mic_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Mic",
                                tint = PureWhite,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Scenario Picker Dialog
    if (showScenarioPicker) {
        AlertDialog(
            onDismissRequest = { showScenarioPicker = false },
            title = {
                Text("बातचीत का विषय चुनें (Choose Scenario)", fontWeight = FontWeight.Bold)
            },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ConversationDataRepository.SCENARIOS) { scenario ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (scenario.id == activeScenario.id) RoyalBlue.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectScenario(scenario)
                                    showScenarioPicker = false
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = scenario.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalBlue
                                )
                                Text(
                                    text = scenario.hindiTitle,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Difficulty: ${scenario.difficulty}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SaffronOrange,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showScenarioPicker = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Post-Chat Report Dialog
    postReport?.let { report ->
        AlertDialog(
            onDismissRequest = { /* Dismiss */ },
            title = {
                Text("🏆 आपकी Speaking रिपोर्ट कार्ड", fontWeight = FontWeight.Bold, color = RoyalBlue)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(shape = RoundedCornerShape(8.dp), color = SuccessGreen.copy(alpha = 0.15f)) {
                            Text("Speaking: ${report.overallSpeakingScore}%", fontWeight = FontWeight.Bold, color = SuccessGreen, modifier = Modifier.padding(6.dp))
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = RoyalBlue.copy(alpha = 0.15f)) {
                            Text("Fluency: ${report.fluencyScore}%", fontWeight = FontWeight.Bold, color = RoyalBlue, modifier = Modifier.padding(6.dp))
                        }
                    }

                    Text(
                        text = "💡 AI गुरुजी की सलाह:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(text = report.hindiSummaryFeedback, style = MaterialTheme.typography.bodySmall)

                    if (report.vocabularyUpgrades.isNotEmpty()) {
                        Text(
                            text = "✨ बेहतर शब्द सुझाव (Upgrades):",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )
                        report.vocabularyUpgrades.forEach { (simple, better) ->
                            Text(text = "• \"$simple\" की जगह \"$better\" बोलें", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.selectScenario(activeScenario) // reset
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                ) {
                    Text("नया सत्र शुरू करें (Start New)")
                }
            }
        )
    }
}

@Composable
fun ChatBubbleItem(
    message: ConversationMessage,
    showHindi: Boolean,
    onSpeakText: (String) -> Unit
) {
    val isAi = message.sender == MessageSender.AI_TEACHER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isAi) Alignment.Start else Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(0.88f)
        ) {
            if (isAi) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(RoyalBlue)
                ) {
                    Text("🤖", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isAi) 2.dp else 16.dp,
                    bottomEnd = if (isAi) 16.dp else 2.dp
                ),
                color = if (isAi) PureWhite else RoyalBlue,
                tonalElevation = 2.dp,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isAi) "AI Teacher" else "You",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isAi) RoyalBlue else LightAmber
                        )

                        if (isAi) {
                            IconButton(
                                onClick = { onSpeakText(message.textEnglish) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Speak",
                                    tint = RoyalBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = message.textEnglish,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isAi) MaterialTheme.colorScheme.onSurface else PureWhite
                    )

                    if (showHindi && message.textHindi != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = message.textHindi,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isAi) SaffronOrange else LightAmber
                        )
                    }

                    if (message.grammarFeedback != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = LightAmber
                        ) {
                            Text(
                                text = "💡 सुधार: ${message.grammarFeedback}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF78350F),
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
