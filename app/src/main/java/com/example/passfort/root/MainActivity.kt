package com.example.passfort

import NavigationGraph
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.root.MainViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*fb.collection("users").document()
            .set(mapOf("name" to "Konda","email" to "konda@gmail.com"))*/


        enableEdgeToEdge()
        setContent {
            PassFortTheme {
                val mainViewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                val UserLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()

                NavigationGraph(navController = navController, UserLoggedIn)
            }
        }
    }
}
