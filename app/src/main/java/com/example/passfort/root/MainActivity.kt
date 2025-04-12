package com.example.passfort

import com.example.passfort.navigation.NavigationGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.root.MainViewModel
import com.example.passfort.root.PreferencesManager

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*fb.collection("users").document()
            .set(mapOf("name" to "Konda","email" to "konda@gmail.com"))*/

        enableEdgeToEdge()
        setContent {
            PassFortTheme {
                val context = LocalContext.current

                val viewModel: MainViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MainViewModel(
                                preferencesManager = PreferencesManager(context)
                            ) as T
                        }
                    }
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
