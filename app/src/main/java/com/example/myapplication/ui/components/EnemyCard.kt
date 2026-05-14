package com.example.myapplication.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.*
import com.example.myapplication.ui.theme.*

@Composable
fun EnemyCard(
    enemy: Enemy,
    onHpChange: (Int) -> Unit,
    onMpChange: (Int) -> Unit,
    onDealDamage: () -> Unit,
    onToggleStatus: (StatusEffect) -> Unit,
    onAffinityChange: (DamageType, Affinity) -> Unit,
    onAddSkill: (Skill) -> Unit,
    onRemoveSkill: (Int) -> Unit,
    onUpdateSkill: (Int, Skill) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // --- Header: Name + Delete ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Crisis indicator
                    if (enemy.isInCrisis) {
                        Text(
                            text = "⚠️",
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = enemy.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (enemy.currentHp <= 0) TextMuted else TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    if (enemy.currentHp <= 0) {
                        Text(
                            text = "💀",
                            fontSize = 14.sp
                        )
                    }
                }
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Usuń",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // --- HP Bar ---
            HpMpBar(
                current = enemy.currentHp,
                max = enemy.maxHp,
                isHp = true,
                isCrisis = enemy.isInCrisis
            )

            // --- MP Bar ---
            HpMpBar(
                current = enemy.currentMp,
                max = enemy.maxMp,
                isHp = false
            )

            // --- Affinity Table (compact) ---
            AffinityTable(
                affinities = enemy.affinities,
                compact = true
            )

            // --- Stats Table ---
            StatsTable(enemy = enemy)

            // --- Quick Buttons ---
            QuickButtons(
                onHpChange = onHpChange,
                onMpChange = onMpChange,
                onDealDamage = onDealDamage
            )

            // --- Expand/Collapse Toggle ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { expanded = !expanded }
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (expanded) "Zwiń" else "Rozwiń",
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryPurple,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Zwiń" else "Rozwiń",
                        tint = PrimaryPurple,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // --- Expanded Section ---
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HorizontalDivider(color = DarkBorder, thickness = 1.dp)

                    // Status Effects
                    StatusEffectsPanel(
                        activeStatuses = enemy.statusEffects,
                        onToggleStatus = onToggleStatus
                    )

                    HorizontalDivider(color = DarkBorder, thickness = 1.dp)

                    // Affinity Manager
                    AffinityManager(
                        affinities = enemy.affinities,
                        onAffinityChange = onAffinityChange
                    )

                    HorizontalDivider(color = DarkBorder, thickness = 1.dp)

                    // Skills
                    SkillsPanel(
                        skills = enemy.skills,
                        onAddSkill = onAddSkill,
                        onRemoveSkill = onRemoveSkill,
                        onUpdateSkill = onUpdateSkill
                    )
                }
            }
        }
    }
}
