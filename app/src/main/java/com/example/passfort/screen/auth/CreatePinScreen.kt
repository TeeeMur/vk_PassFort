package com.example.passfort.screen.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Activity
import androidx.compose.ui.platform.LocalContext

@SuppressLint("ContextCastToActivity")
@Composable
fun CreatePinScreen(
    userName: String = "Арина",
    requiredLength: Int = 5,
    onPinCreated: (String) -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var firstPin by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-100).dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (step == 1) "Придумайте ПИН-код" else "Повторите ПИН-код",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(Modifier.height(40.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(requiredLength) { idx ->
                    PinIndicator(filled = idx < pin.length)
                    if (idx < requiredLength - 1) Spacer(Modifier.width(24.dp))
                }
            }
            if (showError) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "ПИН-коды не совпадают",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PinKeyboard(
                onDigitClick = { digit ->
                    if (pin.length < requiredLength) {
                        pin += digit
                        if (pin.length == requiredLength) {
                            if (step == 1) {
                                firstPin = pin
                                pin = ""
                                step = 2
                                showError = false
                            } else {
                                if (pin == firstPin) {
                                    onPinCreated(pin)
                                } else {
                                    showError = true
                                    firstPin = ""
                                    pin = ""
                                    step = 1
                                }
                            }
                        }
                    }
                },
                onDeleteClick = {
                    if (pin.isNotEmpty()) pin = pin.dropLast(1)
                },
                onExitClick   = { activity?.finish() }

            )
        }
    }
}
