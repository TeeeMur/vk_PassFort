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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.designSystem.components.NavigationBar
import androidx.navigation.navArgument
import com.example.passfort.designSystem.theme.ChosenTheme
import com.example.passfort.model.PreferencesManager
import com.example.passfort.screen.AdditionalSettings
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.MainScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.PasswordCreateModalScreen
import com.example.passfort.screen.passwords.PasswordDetailScreen
import com.example.passfort.screen.passwords.PasswordGeneratorScreen
import com.example.passfort.screen.auth.SettingsScreenNew
import com.example.passfort.viewModel.LoginViewModel

@Composable

fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    onChangeTheme: (ChosenTheme) -> Unit,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    var showBottomSheetCreatePassword by remember { mutableStateOf(false) }

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
            MainScreen(
                onClickPassword = {id: Long -> navController.navigate(Screen.PasswordDetail.createRoute(id))},
                navController = navController,
                navigationBar = { NavigationBar(navController){showBottomSheetCreatePassword = true} },
            )
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController) { showBottomSheetCreatePassword = true }
        }

        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController = navController,
                onClickPassword = {id: Long -> navController.navigate(Screen.PasswordDetail.createRoute(id))},
                onAddPassword = {showBottomSheetCreatePassword = true}
            )
        }

        composable(
            Screen.PasswordDetail.route,
            arguments = listOf(
                navArgument("passwordId"){
                    type = NavType.LongType
                    nullable = false
                }
            )
        )
        {
            it.arguments?.getLong("passwordId")?.let {
                PasswordDetailScreen(
                    idPasswordRecord = it,
                    onBackScreen = { navController.navigateUp() }
                )
            }
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navController,
                onBack = {navController.navigate(Screen.Login.route)},
                onRegisterSuccess = {navController.navigate(Screen.Login.route)}
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreenNew(
                navController = navController,
                onChangeTheme = onChangeTheme,
                navBar = { NavigationBar(navController){showBottomSheetCreatePassword = true} },
            )
        }
        composable(Screen.AdditionalSettings.route) {
            AdditionalSettings(
                onLogout = {
                    onLogout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                onDeleteProfile = { },
                onBack = {navController.navigateUp()},
            )
        }
    }

    PasswordCreateModalScreen(
        showBottomSheetCreatePassword = showBottomSheetCreatePassword,
        onDismiss = { showBottomSheetCreatePassword = false },
    )
}




