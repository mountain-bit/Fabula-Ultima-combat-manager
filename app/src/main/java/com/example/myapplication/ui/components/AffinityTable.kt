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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Affinity
import com.example.myapplication.data.model.DamageType
import com.example.myapplication.ui.theme.*

@Composable
fun AffinityTable(
    affinities: Map<DamageType, Affinity>,
    compact: Boolean = true,
    onAffinityClick: ((DamageType) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceVariant)
            .padding(6.dp)
    ) {
        // Header row - damage type icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DamageType.entries.forEach { type ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = type.icon,
                        fontSize = if (compact) 12.sp else 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Values row - affinity abbreviations
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DamageType.entries.forEach { type ->
                val affinity = affinities[type] ?: Affinity.NONE
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (onAffinityClick != null) {
                                Modifier.clickable { onAffinityClick(type) }
                            } else {
                                Modifier
                            }
                        )
                        .padding(vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = affinity.shortName,
                        fontSize = if (compact) 9.sp else 12.sp,
                        color = affinity.color,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}
