package com.example.passfort.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.model.PreferencesManager
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.HomeScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.SettingsScreen
import com.example.passfort.screen.passwords.PartialBottomSheet
import com.example.passfort.screen.passwords.PasswordGeneratorScreen
import com.example.passfort.viewModel.LoginViewModel

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

            val context = LocalContext.current
            val preferencesManager = remember { PreferencesManager(context.applicationContext) }

            val viewModel: LoginViewModel = viewModel(
                factory = LoginViewModel.provideFactory(preferencesManager)
            )

            val uiState = viewModel.uiState

            LaunchedEffect(uiState.loginSuccess) {
                if (uiState.loginSuccess) {
                    onLoginSuccess()
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    viewModel.consumeLoginSuccessEvent()
                }
            }

            LoginScreen(
                uiState = uiState,
                onUsernameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginAttempt = viewModel::onLoginAttempt,

                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgotPassword = {
                },
                onNavigateToPrivacyPolicy = {
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController) { showBottomSheet = true }
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController) { showBottomSheet = true }
        }

        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController = navController) { showBottomSheet = true }
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                navController,
                onBack = {navController.navigate(Screen.Login.route)},
                onRegisterSuccess = {navController.navigate(Screen.Login.route)}
            )
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




