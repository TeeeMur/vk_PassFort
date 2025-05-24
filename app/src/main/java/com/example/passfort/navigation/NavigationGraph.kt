package com.example.passfort.navigation
import com.example.passfort.screen.auth.PinCodeScreen
import com.example.passfort.screen.auth.CreatePinScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.designSystem.components.NavigationBar
import androidx.navigation.navArgument
import com.example.passfort.model.PreferencesManager
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.MainScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.SettingsScreen
import com.example.passfort.screen.passwords.PasswordCreateModalScreen
import com.example.passfort.screen.passwords.PasswordDetailScreen
import com.example.passfort.screen.passwords.PasswordGenerateModalScreen
import com.example.passfort.screen.passwords.PasswordGeneratorScreen
import com.example.passfort.viewModel.LoginViewModel

@Composable

fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    var showBottomSheetCreatePassword by remember { mutableStateOf(false) }
    var showBottomSheetGeneratePassword by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context.applicationContext) }
    var pinEntered by remember { mutableStateOf(false) }
    NavHost(
        navController = navController,
        startDestination = when {
            !isUserLoggedIn -> Screen.Login.route
            !preferencesManager.hasPin() -> Screen.CreatePin.route
            !pinEntered -> Screen.EnterPin.route
            else -> Screen.HomeScreen.route
        }
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

        composable(Screen.CreatePin.route) {
            CreatePinScreen(
                onPinCreated = { pin ->
                    preferencesManager.savePin(pin)
                    // После создания PIN-кода переход на экран ввода PIN
                    navController.navigate(Screen.EnterPin.route) {
                        popUpTo(Screen.CreatePin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.EnterPin.route) {
            val savedPin = preferencesManager.getPin() ?: ""
            PinCodeScreen(
                correctPin = savedPin,
                onSuccess = {
                    // После успешного ввода PIN переход на основной экран
                    pinEntered = true
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.EnterPin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            MainScreen(navController = navController,
                navigationBar = { NavigationBar(navController){showBottomSheetCreatePassword = true} }
            )
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController) { showBottomSheetCreatePassword = true }
        }

        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController = navController,
                onClickPassword = {id: Long -> navController.navigate(Screen.PasswordDetail.createRoute(id))
                },
                onAddPassword = {showBottomSheetCreatePassword = true})
        }

        composable(
            Screen.PasswordDetail.route,
            arguments = listOf(
                navArgument("passwordId"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        )
        {
            it.arguments?.getInt("passwordId")?.let {
                PasswordDetailScreen(
                    idPasswordRecord = it,
                    onGeneratePassword = { showBottomSheetGeneratePassword = true },
                    OnBackScreen = { navController.navigate(Screen.PasswordList.route) }
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
            SettingsScreen(
                navController,
                onLogout = {
                    onLogout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
            { showBottomSheetCreatePassword = true }
        }
    }

    PasswordCreateModalScreen(
        showBottomSheet = showBottomSheetCreatePassword,
        onDismiss = { showBottomSheetCreatePassword = false },
        onGeneratePassword = {showBottomSheetGeneratePassword = true}
    )

    PasswordGenerateModalScreen(showBottomSheetGeneratePassword) { showBottomSheetGeneratePassword = false }
}




