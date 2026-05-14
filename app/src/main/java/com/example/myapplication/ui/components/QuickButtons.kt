package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

@Composable
fun QuickButtons(
    onHpChange: (Int) -> Unit,
    onMpChange: (Int) -> Unit,
    onDealDamage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // HP buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "HP",
                style = MaterialTheme.typography.labelSmall,
                color = HpRed,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.width(22.dp)
            )
            listOf(-10, -5, -1, 1, 5, 10).forEach { amount ->
                QuickButton(
                    text = if (amount > 0) "+$amount" else "$amount",
                    isPositive = amount > 0,
                    onClick = { onHpChange(amount) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // MP buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MP",
                style = MaterialTheme.typography.labelSmall,
                color = MpBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.width(22.dp)
            )
            listOf(-10, -5, -1, 1, 5, 10).forEach { amount ->
                QuickButton(
                    text = if (amount > 0) "+$amount" else "$amount",
                    isPositive = amount > 0,
                    onClick = { onMpChange(amount) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Deal damage button
        Button(
            onClick = onDealDamage,
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryPurple
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "⚔️ Zadaj Obrażenia",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun QuickButton(
    text: String,
    isPositive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPositive) ButtonPositive.copy(alpha = 0.2f)
            else ButtonNegative.copy(alpha = 0.2f),
            contentColor = if (isPositive) ButtonPositive else ButtonNegative
        ),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
