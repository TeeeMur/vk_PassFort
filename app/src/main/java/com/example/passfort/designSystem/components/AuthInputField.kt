package com.example.passfort.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier,
    backgroundColor: Color
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (isPassword && !passwordVisible)
        PasswordVisualTransformation() else VisualTransformation.None

    val icon = if (passwordVisible)
        ImageVector.vectorResource(R.drawable.outline_visibility_24)
    else
        ImageVector.vectorResource(R.drawable.outline_visibility_off_24)

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            visualTransformation = visualTransformation,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                        )
                    }
                }
            } else null,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            modifier = modifier
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldPreview() {
    MaterialTheme {
        AuthTextField(
            label = "Логин",
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    labelResId: Int,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        // Лейбл поля
        Text(
            text = stringResource(labelResId),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, bottom = 8.dp)
                .padding(horizontal = 25.dp),
            textAlign = TextAlign.Left,
            fontSize = 18.sp
        )

        // Поле ввода
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(
                    color = colorResource(R.color.text_field_color),
                    shape = RoundedCornerShape(15.dp)
                ),
            enabled = isEnabled,
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
            ),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (passwordVisible) R.drawable.icon_button_password_hide
                            else R.drawable.icon_button_password_show
                        ),
                        contentDescription = if (passwordVisible)
                            "Hide password" else "Show password"
                    )
                }
            }
        )
    }
}