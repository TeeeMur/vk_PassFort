package com.example.passfort.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    Column {
        Button(onClick = onRegisterSuccess) {
            Text("Зарегистрироваться (заглушка)")
        }
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}