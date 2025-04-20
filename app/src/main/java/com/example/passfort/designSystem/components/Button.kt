package com.yourpackage.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.passfort.designSystem.theme.PassFortTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    width: Dp = 56.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    textColor: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = Color.Transparent
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .width(width),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        contentPadding = contentPadding,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    width: Dp = 56.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(containerColor, contentColor),
        modifier = modifier
                .width(width)
            .height(height),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .width(width)
                    .height(height),
            )
        } else {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview_Light() {
    PassFortTheme(dynamicColor = false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SecondaryButton(
                text = "Создать аккаунт",
                onClick = {}
            )

            SecondaryButton(
                text = "Другая кнопка",
                onClick = {},
                textColor = Color.Red,
                containerColor = Color.LightGray
            )

            AuthButton(
                text = "войти",
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = false,
                enabled = true
            )

            AuthButton(
                text = "Зарегистрироваться",
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth(),
                isLoading = false,
                enabled = false
            )
        }
    }
}