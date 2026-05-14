package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.data.model.DamageType
import com.example.myapplication.data.model.Enemy
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.theme.*
import com.example.myapplication.ui.viewmodel.CombatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CombatScreen(
    viewModel: CombatViewModel,
    modifier: Modifier = Modifier
) {
    val combats by viewModel.combats.collectAsStateWithLifecycle()
    val activeCombatId by viewModel.activeCombatId.collectAsStateWithLifecycle()
    val enemies by viewModel.enemies.collectAsStateWithLifecycle()

    var showAddEnemyDialog by remember { mutableStateOf(false) }
    var damageDialogEnemy by remember { mutableStateOf<Enemy?>(null) }

    // Add Enemy Dialog
    if (showAddEnemyDialog) {
        AddEnemyDialog(
            onDismiss = { showAddEnemyDialog = false },
            onConfirm = { name, hp, mp, dex, ins, mig, wlp ->
                viewModel.addEnemy(
                    name = name,
                    maxHp = hp,
                    maxMp = mp,
                    dexDie = dex,
                    insDie = ins,
                    migDie = mig,
                    wlpDie = wlp
                )
                showAddEnemyDialog = false
            }
        )
    }

    // Damage Dialog
    damageDialogEnemy?.let { enemy ->
        DamageDialog(
            enemy = enemy,
            onDismiss = { damageDialogEnemy = null },
            onConfirm = { baseDamage, damageType ->
                viewModel.applyDamage(enemy, baseDamage, damageType)
                damageDialogEnemy = null
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // --- Top Bar ---
        TopAppBar(
            title = {
                Text(
                    text = "⚔️ FU Combat Manager",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = DarkSurface
            )
        )

        // --- Combat Tab Bar ---
        CombatTabBar(
            combats = combats,
            activeCombatId = activeCombatId,
            onSelectCombat = { viewModel.selectCombat(it) },
            onAddCombat = { viewModel.addCombat() },
            onDeleteCombat = { viewModel.deleteCombat(it) }
        )

        // --- Enemy List ---
        if (enemies.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🎲",
                        fontSize = 48.sp
                    )
                    Text(
                        text = "Brak przeciwników",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Dodaj pierwszego wroga, aby rozpocząć walkę!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { showAddEnemyDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Dodaj",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Dodaj Przeciwnika")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = enemies,
                    key = { it.id }
                ) { enemy ->
                    EnemyCard(
                        enemy = enemy,
                        onHpChange = { amount -> viewModel.adjustHp(enemy, amount) },
                        onMpChange = { amount -> viewModel.adjustMp(enemy, amount) },
                        onDealDamage = { damageDialogEnemy = enemy },
                        onToggleStatus = { status -> viewModel.toggleStatusEffect(enemy, status) },
                        onAffinityChange = { type, affinity ->
                            viewModel.setAffinity(enemy, type, affinity)
                        },
                        onAddSkill = { skill -> viewModel.addSkill(enemy, skill) },
                        onRemoveSkill = { index -> viewModel.removeSkill(enemy, index) },
                        onUpdateSkill = { index, skill -> viewModel.updateSkill(enemy, index, skill) },
                        onDelete = { viewModel.deleteEnemy(enemy) }
                    )
                }

                // Add enemy button at the bottom
                item {
                    Button(
                        onClick = { showAddEnemyDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryPurple.copy(alpha = 0.15f),
                            contentColor = PrimaryPurple
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Dodaj",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Dodaj Przeciwnika",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Bottom spacer for safe area
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
