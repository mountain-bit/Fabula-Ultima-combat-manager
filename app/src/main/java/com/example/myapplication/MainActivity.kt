package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.screens.CombatScreen
import com.example.myapplication.ui.theme.FUCombatManagerTheme
import com.example.myapplication.ui.viewmodel.CombatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FUCombatManagerTheme {
                val combatViewModel: CombatViewModel = viewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CombatScreen(
                        viewModel = combatViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}