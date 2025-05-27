package com.example.passfort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.passfort.navigation.NavigationGraph
import com.example.passfort.root.MainViewModel
import com.example.passfort.root.MainViewModelFactory
import com.example.passfort.model.PreferencesManager
import com.example.passfort.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import com.example.passfort.designSystem.theme.PassFortTheme

@AndroidEntryPoint
@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            PassFortTheme {
                val viewModel: MainViewModel = hiltViewModel()

                val navController = rememberNavController()
                val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

                LaunchedEffect(isUserLoggedIn) {
                    val currentScreenRoute = navController.currentDestination?.route
                    if (!isUserLoggedIn && currentScreenRoute != Screen.Login.route) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true; saveState = false }
                            launchSingleTop = true
                            restoreState = false
                        }
                    } else if (isUserLoggedIn && currentScreenRoute == Screen.Login.route) {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }

                NavigationGraph(
                    navController = navController,
                    isUserLoggedIn = isUserLoggedIn,
                    onLoginSuccess = { viewModel.login() },
                    onLogout = { viewModel.logout() }
                )
            }
        }
    }
}
