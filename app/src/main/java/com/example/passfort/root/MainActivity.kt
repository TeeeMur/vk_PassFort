package com.example.passfort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.navigation.NavigationGraph
import com.example.passfort.root.MainViewModel
import com.example.passfort.root.MainViewModelFactory
import com.example.passfort.model.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PassFortTheme {
                val context = LocalContext.current
                val preferencesManager = remember { PreferencesManager(context) }
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(preferencesManager)
                )


                val navController = rememberNavController()
                val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

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
