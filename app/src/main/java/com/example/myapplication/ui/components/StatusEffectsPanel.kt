package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.StatusEffect
import com.example.myapplication.ui.theme.*

@Composable
fun StatusEffectsPanel(
    activeStatuses: List<StatusEffect>,
    onToggleStatus: (StatusEffect) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Statusy",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Bold
        )

        // Two rows of 3 status chips
        val rows = StatusEffect.entries.chunked(3)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                row.forEach { status ->
                    val isActive = status in activeStatuses
                    StatusChip(
                        status = status,
                        isActive = isActive,
                        onClick = { onToggleStatus(status) },
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill remaining space if row has fewer items
                repeat(3 - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Info about active effects
        if (activeStatuses.isNotEmpty()) {
            val affectedAttrs = activeStatuses
                .flatMap { it.affectedAttributes }
                .groupingBy { it }
                .eachCount()

            Text(
                text = affectedAttrs.entries.joinToString(", ") { (attr, count) ->
                    "${attr.shortName} ↓$count"
                },
                style = MaterialTheme.typography.labelSmall,
                color = DieReduced,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun StatusChip(
    status: StatusEffect,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = getStatusColor(status)
    val backgroundColor = if (isActive) statusColor.copy(alpha = 0.2f) else Color.Transparent
    val borderColor = if (isActive) statusColor else DarkBorder

    Box(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = status.icon,
                fontSize = 12.sp
            )
            Text(
                text = status.displayName,
                fontSize = 10.sp,
                color = if (isActive) statusColor else TextMuted,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

private fun getStatusColor(status: StatusEffect): Color {
    return when (status) {
        StatusEffect.DAZED -> StatusDazed
        StatusEffect.ENRAGED -> StatusEnraged
        StatusEffect.POISONED -> StatusPoisoned
        StatusEffect.SHAKEN -> StatusShaken
        StatusEffect.SLOW -> StatusSlow
        StatusEffect.WEAK -> StatusWeak
    }
}
