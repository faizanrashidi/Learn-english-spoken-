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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import com.example.model.LevelDataRepository
import com.example.model.LevelItem
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
fun LevelJourneyScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    onSelectLevel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProgress by viewModel.userProgress.collectAsState()
    val allLevels = LevelDataRepository.ALL_LEVELS

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "101 Levels Roadmap",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Level 0 (Beginner) → Level 100 (Mastery)",
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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                RoadmapSummaryCard(unlockedLevel = userProgress.unlockedLevel)
                Spacer(modifier = Modifier.height(16.dp))
            }

            LevelDataRepository.STAGES.forEach { stage ->
                item {
                    StageHeaderCard(stage = stage)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                val stageLevels = allLevels.filter { it.stageNumber == stage.stageNumber }
                items(stageLevels) { levelItem ->
                    val isUnlocked = levelItem.levelNumber <= userProgress.unlockedLevel
                    val isCurrent = levelItem.levelNumber == userProgress.currentLevel
                    val isCompleted = levelItem.levelNumber < userProgress.unlockedLevel

                    LevelItemRow(
                        level = levelItem,
                        isUnlocked = isUnlocked,
                        isCurrent = isCurrent,
                        isCompleted = isCompleted,
                        onClick = {
                            if (isUnlocked) {
                                viewModel.selectLevel(levelItem)
                                onSelectLevel()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun RoadmapSummaryCard(unlockedLevel: Int) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = RoyalBlue),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "🎯 आपकी प्रगति (Progress)",
                    style = MaterialTheme.typography.labelSmall,
                    color = LightAmber
                )
                Text(
                    text = "$unlockedLevel / 100 Levels Unlocked",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = PureWhite.copy(alpha = 0.2f)
            ) {
                Text(
                    text = "10 Stages",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun StageHeaderCard(stage: com.example.model.LevelStage) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SaffronOrange
                ) {
                    Text(
                        text = "Stage ${stage.stageNumber}: ${stage.range}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = stage.hindiSupportLevel,
                    style = MaterialTheme.typography.labelSmall,
                    color = RoyalBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stage.titleHindi,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stage.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LevelItemRow(
    level: LevelItem,
    isUnlocked: Boolean,
    isCurrent: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCurrent -> RoyalBlue.copy(alpha = 0.08f)
                isUnlocked -> PureWhite
                else -> Color(0xFFF1F5F9)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isUnlocked) 1.dp else 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onClick() }
            .then(
                if (isCurrent) Modifier.border(1.5.dp, RoyalBlue, RoundedCornerShape(16.dp))
                else Modifier
            )
            .testTag("level_item_${level.levelNumber}")
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
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isCompleted -> SuccessGreen.copy(alpha = 0.15f)
                                isCurrent -> RoyalBlue.copy(alpha = 0.15f)
                                isUnlocked -> SaffronOrange.copy(alpha = 0.15f)
                                else -> Color(0xFFE2E8F0)
                            }
                        )
                ) {
                    when {
                        isCompleted -> Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Done",
                            tint = SuccessGreen,
                            modifier = Modifier.size(22.dp)
                        )
                        isCurrent -> Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Current",
                            tint = RoyalBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        isUnlocked -> Text(
                            text = "${level.levelNumber}",
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                        else -> Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Level ${level.levelNumber}: ${level.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else Color(0xFF94A3B8)
                        )
                    }

                    Text(
                        text = level.hindiTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isUnlocked) MaterialTheme.colorScheme.onSurfaceVariant else Color(0xFFCBD5E1),
                        maxLines = 1
                    )

                    if (level.isCheckpoint) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WarmAmber.copy(alpha = 0.2f),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                text = "🏆 ${level.checkpointTestTitle}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SaffronOrange,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            if (isUnlocked) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = WarmAmber.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "+${level.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SaffronOrange,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
