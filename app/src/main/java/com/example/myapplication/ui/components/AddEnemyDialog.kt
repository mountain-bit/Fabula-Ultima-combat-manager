package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.DieSize
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEnemyDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, maxHp: Int, maxMp: Int, dex: DieSize, ins: DieSize, mig: DieSize, wlp: DieSize) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var hpText by remember { mutableStateOf("40") }
    var mpText by remember { mutableStateOf("30") }
    var dexDie by remember { mutableStateOf(DieSize.D8) }
    var insDie by remember { mutableStateOf(DieSize.D8) }
    var migDie by remember { mutableStateOf(DieSize.D8) }
    var wlpDie by remember { mutableStateOf(DieSize.D8) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = "Dodaj Przeciwnika",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nazwa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = PrimaryPurple,
                        cursorColor = PrimaryPurple
                    )
                )

                // HP / MP row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = hpText,
                        onValueChange = { hpText = it.filter { c -> c.isDigit() } },
                        label = { Text("Max HP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HpRed,
                            unfocusedBorderColor = DarkBorder,
                            focusedLabelColor = HpRed,
                            cursorColor = HpRed
                        )
                    )
                    OutlinedTextField(
                        value = mpText,
                        onValueChange = { mpText = it.filter { c -> c.isDigit() } },
                        label = { Text("Max MP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MpBlue,
                            unfocusedBorderColor = DarkBorder,
                            focusedLabelColor = MpBlue,
                            cursorColor = MpBlue
                        )
                    )
                }

                // Dice selectors
                Text(
                    text = "Kości bazowe",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DieSelector("DEX", dexDie, { dexDie = it }, Modifier.weight(1f))
                    DieSelector("INS", insDie, { insDie = it }, Modifier.weight(1f))
                    DieSelector("MIG", migDie, { migDie = it }, Modifier.weight(1f))
                    DieSelector("WLP", wlpDie, { wlpDie = it }, Modifier.weight(1f))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val hp = hpText.toIntOrNull() ?: 40
                    val mp = mpText.toIntOrNull() ?: 30
                    onConfirm(
                        name.ifBlank { "Nowy Wróg" },
                        hp, mp, dexDie, insDie, migDie, wlpDie
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Dodaj")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DieSelector(
    label: String,
    selectedDie: DieSize,
    onDieChange: (DieSize) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            fontSize = 10.sp
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedDie.displayName,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .menuAnchor(),
                textStyle = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = DarkBorder,
                    cursorColor = PrimaryPurple
                ),
                singleLine = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = DarkSurfaceVariant
            ) {
                DieSize.entries.forEach { die ->
                    DropdownMenuItem(
                        text = { Text(die.displayName, color = TextPrimary) },
                        onClick = {
                            onDieChange(die)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
