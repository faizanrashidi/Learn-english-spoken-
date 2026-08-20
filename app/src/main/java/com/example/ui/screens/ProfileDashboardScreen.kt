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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SkillProgressBar
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
fun ProfileDashboardScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress by viewModel.userProgress.collectAsState()

    val goals = listOf(
        "Daily Conversation (रोजमर्रा की बातचीत)",
        "Job Interview & Career (इंटरव्यू की तैयारी)",
        "College & Public Speaking (कॉलेज व प्रस्तुति)",
        "Foreign Travel & Visa (विदेश यात्रा)",
        "General Fluency Mastery (पूर्ण धाराप्रवाह अंग्रेजी)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Learner Profile & Stats",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
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
                Spacer(modifier = Modifier.height(14.dp))
                ProfileHeaderCard(
                    userName = progress.userName,
                    level = progress.currentLevel,
                    xp = progress.totalXp,
                    streak = progress.streakDays,
                    isPro = progress.isPremium
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Pro SaaS Subscription Upgrade Banner
            item {
                ProSaaSSubscriptionCard(
                    isPro = progress.isPremium,
                    onTogglePro = { viewModel.toggleProSubscription(!progress.isPremium) }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // 6-Skill Progress Radar
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "📊 6-Skill Mastery Radar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        SkillProgressBar(label = "🎤 Speaking (बोलना)", score = progress.speakingScore, color = SaffronOrange)
                        Spacer(modifier = Modifier.height(8.dp))
                        SkillProgressBar(label = "📚 Grammar (व्याकरण)", score = progress.grammarScore, color = RoyalBlue)
                        Spacer(modifier = Modifier.height(8.dp))
                        SkillProgressBar(label = "📖 Vocabulary (शब्दकोश)", score = progress.vocabularyScore, color = SuccessGreen)
                        Spacer(modifier = Modifier.height(8.dp))
                        SkillProgressBar(label = "🎧 Listening (सुनकर समझना)", score = progress.listeningScore, color = Color(0xFF0284C7))
                        Spacer(modifier = Modifier.height(8.dp))
                        SkillProgressBar(label = "✍️ Writing (लिखना)", score = progress.writingScore, color = Color(0xFFD97706))
                        Spacer(modifier = Modifier.height(8.dp))
                        SkillProgressBar(label = "⚡ Fluency (रवानी)", score = progress.fluencyScore, color = Color(0xFF7C3AED))
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Learning Goal Selector
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "🎯 आपका मुख्य लक्ष्य (Learning Goal):",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        goals.forEach { goalText ->
                            val isSelected = progress.learningGoal == goalText
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) RoyalBlue.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                                    .clickable { viewModel.updateUserGoal(goalText) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Star,
                                        contentDescription = null,
                                        tint = if (isSelected) RoyalBlue else Color(0xFF94A3B8),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = goalText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) RoyalBlue else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun ProfileHeaderCard(
    userName: String,
    level: Int,
    xp: Int,
    streak: Int,
    isPro: Boolean
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = RoyalBlue),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(SaffronOrange)
                    ) {
                        Text("🇮🇳", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Level $level Learner",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightAmber
                        )
                    }
                }

                if (isPro) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = WarmAmber
                    ) {
                        Text(
                            text = "PRO MEMBER",
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileStatColumn("⚡ $xp", "Total XP")
                ProfileStatColumn("🔥 $streak Days", "Streak")
                ProfileStatColumn("🎯 Level $level", "Rank")
            }
        }
    }
}

@Composable
fun ProfileStatColumn(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = LightAmber)
    }
}

@Composable
fun ProSaaSSubscriptionCard(
    isPro: Boolean,
    onTogglePro: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(WarmAmber.copy(alpha = 0.15f), SaffronOrange.copy(alpha = 0.15f))
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "👑 Bol English AI Pro",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                        Text(
                            text = "Unlimited AI Voice Calls • All 101 Levels • 0 Ads",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = isPro,
                        onCheckedChange = { onTogglePro() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PureWhite,
                            checkedTrackColor = SaffronOrange
                        )
                    )
                }
            }
        }
    }
}
