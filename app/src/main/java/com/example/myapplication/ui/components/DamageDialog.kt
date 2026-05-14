package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Affinity
import com.example.myapplication.data.model.DamageType
import com.example.myapplication.data.model.Enemy
import com.example.myapplication.ui.theme.*
import com.example.myapplication.ui.util.DamageCalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DamageDialog(
    enemy: Enemy,
    onDismiss: () -> Unit,
    onConfirm: (baseDamage: Int, damageType: DamageType) -> Unit
) {
    var damageText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(DamageType.PHYSICAL) }
    var expanded by remember { mutableStateOf(false) }

    val baseDamage = damageText.toIntOrNull() ?: 0
    val affinity = enemy.affinities[selectedType] ?: Affinity.NONE
    val result = DamageCalculator.calculateDamage(baseDamage, affinity, selectedType)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = "⚔️ Zadaj Obrażenia",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Target name
                Text(
                    text = "Cel: ${enemy.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Damage amount input
                OutlinedTextField(
                    value = damageText,
                    onValueChange = { damageText = it.filter { c -> c.isDigit() } },
                    label = { Text("Obrażenia") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = PrimaryPurple,
                        cursorColor = PrimaryPurple
                    )
                )

                // Damage type dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = "${selectedType.icon} ${selectedType.displayName}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Rodzaj obrażeń") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryPurple,
                            unfocusedBorderColor = DarkBorder,
                            focusedLabelColor = PrimaryPurple
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        containerColor = DarkSurfaceVariant
                    ) {
                        DamageType.entries.forEach { type ->
                            val typeAffinity = enemy.affinities[type] ?: Affinity.NONE
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = type.icon, fontSize = 16.sp)
                                        Text(
                                            text = type.displayName,
                                            color = TextPrimary
                                        )
                                        if (typeAffinity != Affinity.NONE) {
                                            Text(
                                                text = "(${typeAffinity.shortName})",
                                                color = typeAffinity.color,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Preview result
                if (baseDamage > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Podgląd:",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = result.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (result.isHealing) AffinityAbsorb else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (baseDamage > 0) {
                        onConfirm(baseDamage, selectedType)
                    }
                },
                enabled = baseDamage > 0,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Zatwierdź")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = TextSecondary)
            ) {
                Text("Anuluj")
            }
        }
    )
}
