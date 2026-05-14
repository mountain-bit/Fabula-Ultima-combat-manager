package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Attribute
import com.example.myapplication.data.model.Enemy
import com.example.myapplication.ui.theme.*

@Composable
fun StatsTable(
    enemy: Enemy,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceVariant)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Attribute.entries.forEach { attribute ->
            val effectiveDie = enemy.getEffectiveDie(attribute)
            val modification = enemy.getDieModification(attribute)

            val dieColor = when (modification) {
                -1 -> DieReduced    // status debuff → red
                1 -> DieBuffed      // buff → green (future)
                else -> DieNormal   // unchanged → white
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = attribute.shortName,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
                Text(
                    text = effectiveDie.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    color = dieColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )
            }
        }
    }
}
