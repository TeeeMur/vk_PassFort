package com.example.passfort.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.ui.screen.passwordgen.PasswordGenScreen


@Composable
fun PasswordGeneratorScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { NavigationBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            PasswordGenScreen()
        }
    }
}