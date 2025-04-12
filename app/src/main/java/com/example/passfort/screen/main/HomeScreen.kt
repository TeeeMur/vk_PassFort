package com.example.passfort.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.navigation.PasswordGeneratorScreen
import com.example.passfort.navigation.Screen
import com.example.passfort.screen.passwords.AddPasswordScreen
import com.example.passfort.screen.passwords.PasswordListScreen

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


