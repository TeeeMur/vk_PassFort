package com.example.passfort.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PasswordGeneratorScreen() {
    var generatedPassword by remember { mutableStateOf("") }
    var passwordLength by remember { mutableStateOf(12) }
    var includeNumbers by remember { mutableStateOf(true) }
    var includeSpecialChars by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Генератор паролей", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = generatedPassword,
            onValueChange = {},
            readOnly = true,
            label = { Text("Сгенерированный пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Длина пароля: $passwordLength")
        Slider(
            value = passwordLength.toFloat(),
            onValueChange = { passwordLength = it.toInt() },
            valueRange = 6f..32f,
            steps = 26
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = includeNumbers, onCheckedChange = { includeNumbers = it })
            Text("Включать цифры")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = includeSpecialChars, onCheckedChange = { includeSpecialChars = it })
            Text("Включать спецсимволы")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            generatedPassword = generatePassword(passwordLength, includeNumbers, includeSpecialChars)
        }) {
            Text("Сгенерировать")
        }
    }
}

fun generatePassword(length: Int, useNumbers: Boolean, useSpecialChars: Boolean): String {
    val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val specialChars = "!@#$%^&*()-_=+<>?/"

    var charPool = letters
    if (useNumbers) charPool += numbers
    if (useSpecialChars) charPool += specialChars

    return (1..length)
        .map { charPool.random() }
        .joinToString("")
}