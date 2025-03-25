package com.example.passfort.screen.main

import PasswordListScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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

@Composable
fun HomeScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { NavigationBar(bottomNavController) }  // Теперь используем NavigationBar из designSystem
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.PasswordList.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.PasswordList.route) { PasswordListScreen() }
            composable(Screen.AddPassword.route) { AddPasswordScreen() }
            composable(Screen.PasswordGenerator.route) { PasswordGeneratorScreen() }
        }
    }
}