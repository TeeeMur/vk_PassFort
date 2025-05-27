package com.example.passfort.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.theme.ChosenTheme
import com.example.passfort.navigation.NavigationGraph
import com.example.passfort.model.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.passfort.designSystem.theme.PassFortTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val preferencesManager = remember { PreferencesManager(context) }
            var theme by remember {mutableStateOf(ChosenTheme.valueOf(preferencesManager.getTheme()))}
            PassFortTheme(
                darkTheme = theme
            ) {
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(preferencesManager)
                )

                val navController = rememberNavController()
                val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

                NavigationGraph(
                    navController = navController,
                    isUserLoggedIn = isUserLoggedIn,
                    onLoginSuccess = { viewModel.login() },
                    onChangeTheme = {theme = it},
                    onLogout = { viewModel.logout() }
                )
            }
        }
    }
}
