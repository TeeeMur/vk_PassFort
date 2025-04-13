package com.example.passfort.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.HomeScreen
import com.example.passfort.screen.passwords.AddPasswordScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.SettingsScreen

@Composable

fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) Screen.HomeScreen.route
        else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    onLoginSuccess()
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    onLoginSuccess()
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController)
        }
        composable(Screen.AddPassword.route) {
            AddPasswordScreen(navController)
        }
        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                navController,
                onLogout = {
                    onLogout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}




