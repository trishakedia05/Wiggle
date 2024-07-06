package com.example.wiggle.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.wiggle.core.presentation.components.StandardScaffold

@Composable
fun WSettingsScreen(navController: NavController){
    StandardScaffold(navController) {
        Column(modifier=Modifier.fillMaxSize()){
            Button(
                onClick = {navController.navigate(Screen.EditProfileScreen.route) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
            {
                Text("Edit Profile", color = Color.White)
            }
            Button(
                onClick = {navController.navigate(Screen.SearchScreen.route)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
            {
                Text("Search", color = Color.White)
            }
            Button(
                onClick = {navController.navigate(Screen.CreatePostScreen.route)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
            {
                Text("Create Post", color = Color.White)
            }
            Button(
                onClick = {navController.navigate(Screen.ActivityScreen.route)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
            {
                Text("Activity", color = Color.White)
            }
            Button(
                onClick = {navController.navigate(Screen.PostDetailScreen.route)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
            {
                Text("Post detail", color = Color.White)
            }
        }
    }
}