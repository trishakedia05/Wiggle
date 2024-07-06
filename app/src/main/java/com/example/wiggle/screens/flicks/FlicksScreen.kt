package com.example.wiggle.screens.flicks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wiggle.core.presentation.components.StandardScaffold

@Composable
fun WFlicksScreen(navController: NavController){
    StandardScaffold(navController)
            {
                Text(text = "FLICKS")

            }
    }