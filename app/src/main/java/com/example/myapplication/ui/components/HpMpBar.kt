package com.example.myapplication.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

@Composable
fun HpMpBar(
    current: Int,
    max: Int,
    isHp: Boolean,
    isCrisis: Boolean = false,
    modifier: Modifier = Modifier
) {
    val fraction = if (max > 0) (current.toFloat() / max).coerceIn(0f, 1f) else 0f
    val label = if (isHp) "HP" else "MP"

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "bar_fraction"
    )

    // Crisis pulsing animation
    val infiniteTransition = rememberInfiniteTransition(label = "crisis_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val barAlpha = if (isHp && isCrisis) pulseAlpha else 1f

    val gradientColors = if (isHp) {
        listOf(HpRedDark, HpRed)
    } else {
        listOf(MpBlueDark, MpBlue)
    }

    val animatedBarColor by animateColorAsState(
        targetValue = if (isHp && isCrisis) HpCrisis else gradientColors.last(),
        animationSpec = tween(300),
        label = "bar_color"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(DarkSurfaceVariant)
    ) {
        // Filled portion
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedFraction)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = if (isHp && isCrisis) {
                            listOf(HpRedDark.copy(alpha = barAlpha), animatedBarColor.copy(alpha = barAlpha))
                        } else {
                            gradientColors
                        }
                    )
                )
        )

        // Text overlay
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Text(
                text = "$current / $max",
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}
