package com.example.passfort.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.passfort.designSystem.components.NavigationBar

@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(
        bottomBar = { NavigationBar(navController) }
    ) { innerPadding ->
        Text(
            text = "Android",
            modifier = Modifier.padding(innerPadding)
        )
    }
}


