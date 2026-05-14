package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Combat
import com.example.myapplication.ui.theme.*

@Composable
fun CombatTabBar(
    combats: List<Combat>,
    activeCombatId: Long?,
    onSelectCombat: (Long) -> Unit,
    onAddCombat: () -> Unit,
    onDeleteCombat: (Combat) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkSurface, DarkBackground)
                )
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        combats.forEach { combat ->
            val isActive = combat.id == activeCombatId
            CombatTab(
                combat = combat,
                isActive = isActive,
                onClick = { onSelectCombat(combat.id) },
                onDelete = { onDeleteCombat(combat) },
                showDelete = combats.size > 1
            )
        }

        // Add combat button
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PrimaryPurple.copy(alpha = 0.15f))
                .border(1.dp, PrimaryPurple.copy(alpha = 0.4f), CircleShape)
                .clickable(onClick = onAddCombat),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Dodaj walkę",
                tint = PrimaryPurple,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun CombatTab(
    combat: Combat,
    isActive: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    showDelete: Boolean
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            containerColor = DarkSurface,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    "Usuń walkę?",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    "Czy na pewno chcesz usunąć walkę \"${combat.name}\"? Wszyscy przeciwnicy zostaną usunięci.",
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HpRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Usuń")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteConfirm = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = TextSecondary)
                ) {
                    Text("Anuluj")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isActive) Brush.horizontalGradient(
                    colors = listOf(PrimaryPurpleDark, PrimaryPurple)
                )
                else Brush.horizontalGradient(
                    colors = listOf(DarkSurfaceVariant, DarkSurfaceVariant)
                )
            )
            .border(
                width = 1.dp,
                color = if (isActive) PrimaryPurple else DarkBorder,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = combat.name,
                style = MaterialTheme.typography.labelMedium,
                color = if (isActive) TextPrimary else TextSecondary,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp
            )
            if (showDelete && isActive) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Usuń walkę",
                    tint = TextSecondary,
                    modifier = Modifier
                        .size(14.dp)
                        .clickable { showDeleteConfirm = true }
                )
            }
        }
    }
}
