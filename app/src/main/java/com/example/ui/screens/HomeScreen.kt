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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LevelDataRepository
import com.example.ui.components.SkillProgressBar
import com.example.ui.theme.PureWhite
import com.example.ui.theme.VibrantBackground
import com.example.ui.theme.VibrantOutline
import com.example.ui.theme.VibrantPrimary
import com.example.ui.theme.VibrantPrimaryBorder
import com.example.ui.theme.VibrantPrimaryContainer
import com.example.ui.theme.VibrantPrimaryDark
import com.example.ui.theme.VibrantSkyActive
import com.example.ui.theme.VibrantSkyBorder
import com.example.ui.theme.VibrantSkyContainer
import com.example.ui.theme.VibrantSkyDark
import com.example.ui.theme.VibrantSkyText
import com.example.ui.theme.VibrantSurface
import com.example.ui.theme.VibrantSurfaceActive
import com.example.ui.theme.VibrantSurfaceCard
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary
import com.example.ui.viewmodel.LearningViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: LearningViewModel,
    onNavigateToLevels: () -> Unit,
    onNavigateToLesson: () -> Unit,
    onNavigateToTalkAi: () -> Unit,
    onNavigateToGrammar: () -> Unit,
    onNavigateToDailyPractice: () -> Unit,
    onNavigateToMistakes: () -> Unit,
    onNavigateToExam: () -> Unit,
    onNavigateToVocab: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProgress by viewModel.userProgress.collectAsState()
    val mistakes by viewModel.mistakeList.collectAsState()
    val currentLevelItem = LevelDataRepository.ALL_LEVELS.getOrElse(userProgress.currentLevel) {
        LevelDataRepository.ALL_LEVELS.first()
    }

    Scaffold(
        containerColor = VibrantBackground,
        topBar = {
            // Header matching Vibrant Theme: Namaste 👋 | Faizan Khan | 🔥 12 Days
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Namaste 👋",
                            style = MaterialTheme.typography.labelMedium,
                            color = VibrantTextSecondary
                        )
                        Text(
                            text = userProgress.userName.ifEmpty { "Faizan Khan" },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = VibrantTextPrimary
                        )
                    }
                },
                actions = {
                    // Streak Pill: bg-[#EADDFF] border border-[#D0BCFF] text-[#21005D]
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = VibrantPrimaryContainer,
                        border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onNavigateToProfile() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("🔥", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${userProgress.streakDays} Days",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VibrantPrimaryDark
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VibrantBackground
                )
            )
        },
        bottomBar = {
            // Vibrant Bottom Navigation: bg-white border-t border-[#CAC4D0]
            Surface(
                color = PureWhite,
                border = androidx.compose.foundation.BorderStroke(1.dp, VibrantOutline.copy(alpha = 0.5f)),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Active Home Tab (bg-[#EADDFF] px-5 py-1 rounded-full text-[#6750A4])
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Already home */ }
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = VibrantPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🏠",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VibrantPrimary
                        )
                    }

                    // Practice Tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onNavigateToDailyPractice() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("💬", fontSize = 18.sp)
                        Text(
                            text = "Practice",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibrantTextSecondary
                        )
                    }

                    // Scenario Tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onNavigateToTalkAi() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("🌏", fontSize = 18.sp)
                        Text(
                            text = "Scenario",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibrantTextSecondary
                        )
                    }

                    // Progress Tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onNavigateToProfile() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("📊", fontSize = 18.sp)
                        Text(
                            text = "Progress",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibrantTextSecondary
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(VibrantBackground)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Hero Current Level Card: bg-[#EADDFF] rounded-[28px] border border-[#D0BCFF]
            item {
                VibrantCurrentLevelCard(
                    levelNumber = userProgress.currentLevel,
                    title = currentLevelItem.title,
                    hindiTitle = currentLevelItem.hindiTitle,
                    accuracyScore = userProgress.speakingScore.coerceAtLeast(68),
                    onContinueLearning = {
                        viewModel.selectLevel(currentLevelItem)
                        onNavigateToLesson()
                    },
                    onViewRoadmap = onNavigateToLevels
                )
            }

            // Talk with AI Section: bg-[#D3E3FD] rounded-[24px] border border-[#A8C7FA]
            item {
                VibrantTalkWithAiCard(
                    onTalkWithAi = onNavigateToTalkAi
                )
            }

            // Quick Hub Grid (2 Columns, 24px rounded, border #CAC4D0, bg #F3EDF7)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        VibrantHubCard(
                            emoji = "📚",
                            title = "Grammar",
                            subtitle = "Complete A-Z Rules",
                            onClick = onNavigateToGrammar,
                            modifier = Modifier.weight(1f)
                        )

                        VibrantHubCard(
                            emoji = "❌",
                            title = "My Mistakes",
                            subtitle = "Review ${mistakes.size.coerceAtLeast(14)} errors",
                            onClick = onNavigateToMistakes,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        VibrantHubCard(
                            emoji = "❓",
                            title = "Daily Test",
                            subtitle = "Mixed Questions",
                            onClick = onNavigateToDailyPractice,
                            modifier = Modifier.weight(1f)
                        )

                        val nextMilestone = ((userProgress.currentLevel / 10) + 1) * 10
                        VibrantHubCard(
                            emoji = "🏆",
                            title = "Exam",
                            subtitle = "Next at Level $nextMilestone",
                            onClick = onNavigateToExam,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        VibrantHubCard(
                            emoji = "📖",
                            title = "Vocab Vault",
                            subtitle = "Hindi Phonetics",
                            onClick = onNavigateToVocab,
                            modifier = Modifier.weight(1f)
                        )

                        VibrantHubCard(
                            emoji = "🗺️",
                            title = "Roadmap",
                            subtitle = "Level 0 → Level 100",
                            onClick = onNavigateToLevels,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Domain Fluency & Skills Summary Card
            item {
                VibrantProgressSummaryCard(
                    speaking = userProgress.speakingScore,
                    grammar = userProgress.grammarScore,
                    vocab = userProgress.vocabularyScore,
                    fluency = userProgress.fluencyScore,
                    speakingMins = userProgress.totalSpeakingMinutes,
                    onClick = onNavigateToProfile
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Hero Current Level Card matching:
 * bg-[#EADDFF] rounded-[28px] p-6 border border-[#D0BCFF]
 */
@Composable
fun VibrantCurrentLevelCard(
    levelNumber: Int,
    title: String,
    hindiTitle: String,
    accuracyScore: Int,
    onContinueLearning: () -> Unit,
    onViewRoadmap: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = VibrantPrimaryContainer
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CURRENT LEVEL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = VibrantPrimaryDark.copy(alpha = 0.7f),
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Level $levelNumber",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = VibrantPrimaryDark
                    )
                    Text(
                        text = "$title • $hindiTitle",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VibrantPrimaryDark.copy(alpha = 0.85f),
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Circular Progress Badge: bg-[#21005D] text-white rounded-full w-12 h-12
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(VibrantPrimaryDark)
                ) {
                    Text(
                        text = "$accuracyScore%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar: track-[#D0BCFF], fill-[#6750A4]
            LinearProgressIndicator(
                progress = { accuracyScore / 100f },
                color = VibrantPrimary,
                trackColor = VibrantPrimaryBorder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Continue Learning Button: bg-[#6750A4] text-white rounded-xl py-3
            Button(
                onClick = onContinueLearning,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VibrantPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("continue_learning_button")
            ) {
                Text(
                    text = "Continue Learning",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }
        }
    }
}

/**
 * Talk with AI Card matching:
 * bg-[#D3E3FD] rounded-[24px] p-5 border border-[#A8C7FA]
 */
@Composable
fun VibrantTalkWithAiCard(
    onTalkWithAi: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = VibrantSkyContainer
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, VibrantSkyBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onTalkWithAi() }
            .testTag("talk_with_ai_card")
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "🎤 Talk with AI",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = VibrantSkyText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Practice natural conversation with instant feedback",
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantSkyText.copy(alpha = 0.75f)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Circular Mic: w-12 h-12 bg-[#004A77] rounded-full
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(VibrantSkyDark)
            ) {
                Text("🎙️", fontSize = 22.sp)
            }
        }
    }
}

/**
 * Quick Hub Card matching:
 * bg-[#F3EDF7] p-4 rounded-[24px] border border-[#CAC4D0]
 */
@Composable
fun VibrantHubCard(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = VibrantSurfaceCard
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, VibrantOutline),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = modifier
            .clickable { onClick() }
            .testTag("hub_card_${title.lowercase().replace(" ", "_")}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = VibrantTextPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = VibrantTextSecondary,
                maxLines = 1
            )
        }
    }
}

/**
 * Fluency & Skill Progress Breakdown Card
 */
@Composable
fun VibrantProgressSummaryCard(
    speaking: Int,
    grammar: Int,
    vocab: Int,
    fluency: Int,
    speakingMins: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = PureWhite
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, VibrantOutline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "📊 आपकी प्रगति (Skill Progress)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VibrantTextPrimary
                )
                Text(
                    text = "⏱️ ${speakingMins}m spoken",
                    style = MaterialTheme.typography.labelSmall,
                    color = VibrantPrimaryDark,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SkillProgressBar(label = "🗣️ Speaking Accuracy (बोलने की सटीकता)", score = speaking, color = VibrantPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            SkillProgressBar(label = "📚 Grammar Precision (व्याकरण शुद्धि)", score = grammar, color = VibrantSkyDark)
            Spacer(modifier = Modifier.height(8.dp))
            SkillProgressBar(label = "📖 Vocabulary Depth (शब्द भंडार)", score = vocab, color = Color(0xFF10B981))
            Spacer(modifier = Modifier.height(8.dp))
            SkillProgressBar(label = "⚡ Fluency & Flow (रवानी)", score = fluency, color = Color(0xFF7C3AED))
        }
    }
}
