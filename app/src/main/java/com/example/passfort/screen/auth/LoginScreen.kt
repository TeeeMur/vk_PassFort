package com.example.passfort.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    Column {
        Button(onClick = onLoginClick) {
            Text("Войти (заглушка)")
        }
        Button(onClick = onRegisterClick) {
            Text("Зарегистрироваться")
        }
    }
}