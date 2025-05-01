package com.example.passfort.screen.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passfort.viewModel.PinCodeState
import com.example.passfort.viewModel.PinCodeViewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PinCodeScreen(
    userName: String = "Арина Асхабова",
    viewModel: PinCodeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pin by viewModel.pin.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is PinCodeState.Input -> {
                PinCodeInputScreen(
                    userName = userName,
                    pin = pin,
                    onDigitClick = viewModel::onDigitClick,
                    onDeleteClick = viewModel::onDelete
                )
            }
            is PinCodeState.Loading -> {
                LoadingScreen(userName)
            }
            is PinCodeState.Error -> {
                PinCodeInputScreen(
                    userName = userName,
                    pin = pin,
                    onDigitClick = viewModel::onDigitClick,
                    onDeleteClick = viewModel::onDelete
                )
                ErrorDialog(
                    message = (uiState as PinCodeState.Error).message,
                    onDismiss = { viewModel.retry() }
                )
            }
        }
    }
}

@Composable
fun PinCodeInputScreen(
    userName: String,
    pin: String,
    onDigitClick: (Char) -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-50).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Введите ПИН-код",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) { index ->
                    val filled = index < pin.length
                    PinIndicator(filled = filled)
                    if (index != 4) {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PinKeyboard(
                onDigitClick = onDigitClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun PinIndicator(filled: Boolean) {
    val size = 16.dp
    val borderColor = MaterialTheme.colorScheme.primary
    val fillColor = if (filled) borderColor else Color.Transparent

    Box(
        modifier = Modifier
            .size(size)
            .border(2.dp, borderColor, CircleShape)
            .background(fillColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
    }
}


@Composable
fun PinKeyboard(
    onDigitClick: (Char) -> Unit,
    onDeleteClick: () -> Unit
) {
    val rows = listOf(
        listOf('1','2','3'),
        listOf('4','5','6'),
        listOf('7','8','9'),
        listOf(' ', '0', ' ')
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { digit ->
                    if (digit == ' ') {
                        Box(modifier = Modifier.size(80.dp))
                    } else {
                        PinKey(digit, onDigitClick, onDeleteClick)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PinKey(
    digit: Char,
    onDigitClick: (Char) -> Unit,
    onDeleteClick: () -> Unit
) {
    val size = 80.dp
    val borderColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .size(size)
            .border(2.dp, borderColor, CircleShape)
            .background(Color.Transparent, CircleShape)
            .clickable {
                if (digit == '<') {
                    onDeleteClick()
                } else {
                    onDigitClick(digit)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (digit == '<') "⌫" else digit.toString(),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Normal),
            color = if (digit == '<') borderColor else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingScreen(userName: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userName, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(" ")
        }
    }
}

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ошибка") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Понятно")
            }
        }
    )
}
