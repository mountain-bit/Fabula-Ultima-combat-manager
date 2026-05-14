package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Skill
import com.example.myapplication.ui.theme.*

@Composable
fun SkillsPanel(
    skills: List<Skill>,
    onAddSkill: (Skill) -> Unit,
    onRemoveSkill: (Int) -> Unit,
    onUpdateSkill: (Int, Skill) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddForm by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Umiejętności",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Bold
        )

        // Existing skills
        skills.forEachIndexed { index, skill ->
            SkillItem(
                skill = skill,
                onRemove = { onRemoveSkill(index) },
                onUpdate = { updatedSkill -> onUpdateSkill(index, updatedSkill) }
            )
        }

        // Add skill form
        if (showAddForm) {
            AddSkillForm(
                onAdd = { skill ->
                    onAddSkill(skill)
                    showAddForm = false
                },
                onCancel = { showAddForm = false }
            )
        } else {
            TextButton(
                onClick = { showAddForm = true },
                colors = ButtonDefaults.textButtonColors(contentColor = PrimaryPurple)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Dodaj",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Dodaj umiejętność",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun SkillItem(
    skill: Skill,
    onRemove: () -> Unit,
    onUpdate: (Skill) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceVariant)
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "• ${skill.name}",
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            if (skill.description.isNotBlank()) {
                Text(
                    text = skill.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Usuń",
                tint = TextMuted,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun AddSkillForm(
    onAdd: (Skill) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceVariant)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa", fontSize = 11.sp) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            textStyle = MaterialTheme.typography.bodySmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = DarkBorder,
                focusedLabelColor = PrimaryPurple,
                cursorColor = PrimaryPurple
            )
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis", fontSize = 11.sp) },
            modifier = Modifier.fillMaxWidth().height(70.dp),
            textStyle = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = DarkBorder,
                focusedLabelColor = PrimaryPurple,
                cursorColor = PrimaryPurple
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onCancel,
                colors = ButtonDefaults.textButtonColors(contentColor = TextSecondary)
            ) {
                Text("Anuluj", fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onAdd(Skill(name = name.trim(), description = description.trim()))
                    }
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Dodaj", fontSize = 11.sp)
            }
        }
    }
}
