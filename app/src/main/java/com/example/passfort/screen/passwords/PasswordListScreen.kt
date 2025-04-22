package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.passfort.designSystem.components.NavigationBar

@Composable
fun PasswordListScreen(navController: NavHostController, onAddPassword: () -> Unit) {
    Scaffold(
        bottomBar = { NavigationBar(navController, onAddPassword)}
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PasswordListScreen",
            )
        }
    }
}