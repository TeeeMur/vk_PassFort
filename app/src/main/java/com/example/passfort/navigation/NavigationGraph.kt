package com.example.passfort.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.HomeScreen
import com.example.passfort.screen.passwords.AddPasswordScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.SettingsScreen
import com.example.passfort.ui.screen.passwordcreate.PartialBottomSheet

@Composable

fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

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
            HomeScreen(navController) { showBottomSheet = true }
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController) { showBottomSheet = true }
        }
        composable(Screen.AddPassword.route) {
            AddPasswordScreen(navController) { showBottomSheet = true }
        }
        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController) { showBottomSheet = true }
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
            { showBottomSheet = true }
        }
    }
    PartialBottomSheet(showBottomSheet) { showBottomSheet = false }
}




