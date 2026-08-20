package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedLight
import com.example.ui.theme.LightAmber
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.VibrantOutline
import com.example.ui.theme.VibrantPrimary
import com.example.ui.theme.VibrantPrimaryBorder
import com.example.ui.theme.VibrantPrimaryContainer
import com.example.ui.theme.VibrantPrimaryDark
import com.example.ui.theme.VibrantSkyContainer
import com.example.ui.theme.VibrantSkyDark
import com.example.ui.theme.VibrantSurfaceCard
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary
import com.example.ui.theme.WarmAmber

@Composable
fun PulsingMicButton(
    isListening: Boolean,
    amplitude: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String = "🎤 बोलिए (SPEAK NOW)"
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (isListening) 1.25f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isListening) 600 else 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(110.dp)
        ) {
            // Animated Outer Ring
            Box(
                modifier = Modifier
                    .size(if (isListening) (95 * pulseScale).dp else 85.dp)
                    .clip(CircleShape)
                    .background(
                        if (isListening) VibrantSkyDark.copy(alpha = 0.25f)
                        else VibrantPrimary.copy(alpha = 0.15f)
                    )
            )

            // Inner Action Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(
                        if (isListening) VibrantSkyDark
                        else VibrantPrimary
                    )
                    .clickable { onClick() }
                    .testTag("pulse_mic_action_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = PureWhite,
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isListening) "🔴 सुन रहे हैं... बोलें!" else buttonText,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (isListening) VibrantSkyDark else VibrantPrimaryDark
        )

        Text(
            text = if (isListening) "समाप्त करने के लिए दोबारा टैप करें" else "माइक दबाएं और अंग्रेजी में बोलें",
            style = MaterialTheme.typography.bodySmall,
            color = VibrantTextSecondary
        )
    }
}

@Composable
fun WaveformVisualizer(
    isListening: Boolean,
    amplitude: Float,
    modifier: Modifier = Modifier
) {
    if (!isListening) return

    val barCount = 14
    val clampedAmp = amplitude.coerceIn(0.1f, 1.0f)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(vertical = 4.dp)
    ) {
        for (i in 0 until barCount) {
            val factor = 0.3f + (0.7f * ((i + 1) % 4) / 3f) * clampedAmp
            val barHeight = (8 + (30 * factor)).dp

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(barHeight)
                    .padding(horizontal = 1.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (i % 2 == 0) VibrantPrimary else VibrantSkyDark
                    )
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}

@Composable
fun PronunciationAudioButtons(
    onPlayNormal: () -> Unit,
    onPlaySlow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = VibrantPrimaryContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder),
            modifier = Modifier
                .weight(1f)
                .clickable { onPlayNormal() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Normal Audio",
                    tint = VibrantPrimaryDark,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "1.0x सामान्य सुनें",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = VibrantPrimaryDark
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = VibrantSkyContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFFA8C7FA)),
            modifier = Modifier
                .weight(1f)
                .clickable { onPlaySlow() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Slow Audio",
                    tint = VibrantSkyDark,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "0.7x धीमी आवाज",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = VibrantSkyDark
                )
            }
        }
    }
}

@Composable
fun FeedbackCard(
    isCorrect: Boolean,
    accuracyScore: Int,
    learnerSaid: String,
    correctSentence: String,
    hindiExplanation: String,
    hindiPraise: String,
    onSpeakAgain: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) SuccessGreenLight else VibrantSurfaceCard
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCorrect) SuccessGreen.copy(alpha = 0.3f) else VibrantOutline
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // Header: Accuracy & Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isCorrect) SuccessGreen else ErrorRed)
                    ) {
                        Icon(
                            imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isCorrect) "बहुत बढ़िया! (Great Job!)" else "सुधार की आवश्यकता है (Improve)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) SuccessGreen else ErrorRed
                        )
                        Text(
                            text = "सटीकता: $accuracyScore%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = VibrantTextSecondary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isCorrect) SuccessGreen else VibrantPrimary
                ) {
                    Text(
                        text = "+${accuracyScore / 2} XP",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // You Said
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = PureWhite,
                border = androidx.compose.foundation.BorderStroke(1.dp, VibrantOutline.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "आपने बोला (You said):",
                        style = MaterialTheme.typography.labelSmall,
                        color = VibrantTextSecondary
                    )
                    Text(
                        text = "\"$learnerSaid\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCorrect) SuccessGreen else ErrorRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Correct Sentence
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = VibrantPrimaryContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "सही वाक्य (Correct Sentence):",
                        style = MaterialTheme.typography.labelSmall,
                        color = VibrantPrimaryDark,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "\"$correctSentence\"",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = VibrantPrimaryDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hindi Explanation
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = LightAmber,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Explanation",
                        tint = SaffronOrange,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "💡 AI गुरुजी की सीख (Why & Rule):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                        Text(
                            text = hindiExplanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = hindiPraise,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = VibrantPrimary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun XpStreakHeader(
    xp: Int,
    streak: Int,
    level: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Level Chip
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = VibrantPrimaryContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder),
            modifier = Modifier.testTag("level_chip")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "🎯 Level $level",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VibrantPrimaryDark
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Streak Badge (Matching Vibrant Theme: bg-[#EADDFF] border border-[#D0BCFF] text-[#21005D])
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = VibrantPrimaryContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, VibrantPrimaryBorder)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("🔥 ", fontSize = 14.sp)
                    Text(
                        text = "$streak Days",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = VibrantPrimaryDark
                    )
                }
            }

            // XP
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = LightAmber,
                border = androidx.compose.foundation.BorderStroke(1.dp, SaffronOrange.copy(alpha = 0.3f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "⚡ $xp XP",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )
                }
            }
        }
    }
}

@Composable
fun SkillProgressBar(
    label: String,
    score: Int,
    color: Color
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = VibrantTextPrimary
            )
            Text(
                text = "$score%",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { score / 100f },
            color = color,
            trackColor = VibrantPrimaryBorder.copy(alpha = 0.4f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        )
    }
}
