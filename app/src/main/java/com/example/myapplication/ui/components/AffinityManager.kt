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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Affinity
import com.example.myapplication.data.model.DamageType
import com.example.myapplication.ui.theme.*

@Composable
fun AffinityManager(
    affinities: Map<DamageType, Affinity>,
    onAffinityChange: (DamageType, Affinity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Odporności",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Bold
        )

        DamageType.entries.forEach { type ->
            val currentAffinity = affinities[type] ?: Affinity.NONE

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Damage type icon + name
                Text(
                    text = "${type.icon} ${type.displayName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextPrimary,
                    modifier = Modifier.width(100.dp),
                    fontSize = 11.sp
                )

                // Affinity buttons
                Affinity.entries.forEach { affinity ->
                    val isSelected = currentAffinity == affinity
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(26.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isSelected) affinity.color.copy(alpha = 0.25f)
                                else androidx.compose.ui.graphics.Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) affinity.color else DarkBorder,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable { onAffinityChange(type, affinity) }
                            .padding(horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = affinity.shortName,
                            fontSize = 9.sp,
                            color = if (isSelected) affinity.color else TextMuted,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
