package com.example.passfort.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordField
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.LoginUiState
import com.yourpackage.ui.components.AuthButton


@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginAttempt: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clipToBounds()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxWidth(0.4f),
                imageVector = ImageVector.vectorResource(R.drawable.navbar_home),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            LoginForm(
                username = uiState.username,
                password = uiState.password,
                isLoading = uiState.isLoading,
                usernameError = uiState.usernameError,
                passwordError = uiState.passwordError,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                onLogin = onLoginAttempt,
                onRegister = onNavigateToRegister,
                onForgotPassword = onNavigateToForgotPassword,
                onPrivacyPolicy = onNavigateToPrivacyPolicy
            )
        }
    }
}

@Composable
fun LoginForm(
    username: String,
    password: String,
    isLoading: Boolean,
    usernameError: String?,
    passwordError: String?,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onPrivacyPolicy: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InputFieldWithCopy(
                value = username,
                onValueChange = onUsernameChange,
                resourceString = R.string.login_username_label,
                isReadOnly = isLoading,
                isPassword = false,
                isCopy = false,
                isLoginScreen = true
            )

            if (usernameError != null) {
                Text(
                    text = usernameError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp)
                        .padding(horizontal = 25.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            PasswordField(
                value = password,
                onValueChange = onPasswordChange,
                labelResId = R.string.login_password_label,
                isEnabled = !isLoading,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (passwordError != null) {
                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp)
                        .padding(horizontal = 25.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.login_forgot_password_button),
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable(enabled = !isLoading, onClick = onForgotPassword)
                    .padding(start = 8.dp)
                    .padding(horizontal = 25.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            AuthButton(
                text = stringResource(R.string.login_login_button),
                onClick = onLogin,
                isLoading = isLoading,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                fillMaxWidth = true,
            )

            Spacer(modifier = Modifier.height(4.dp))

            AuthButton(
                text = stringResource(R.string.login_register_button),
                onClick = onRegister,
                isLoading = false,
                enabled = !isLoading,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                fillMaxWidth = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.login_privacy_policy_button),
                style = MaterialTheme.typography.labelSmall.copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(enabled = !isLoading, onClick = onPrivacyPolicy),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Surface {
        val previewState = LoginUiState(
            username = "preview@user.com",
            password = "password",
            isLoading = false,
            usernameError = "2323",
            passwordError = "2ewq"
        )
        LoginScreen(
            uiState = previewState,
            onUsernameChange = {},
            onPasswordChange = {},
            onLoginAttempt = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {},
            onNavigateToPrivacyPolicy = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldWithCopyPreview() {
    // Задаем тестовую тему, если у вас есть кастомная тема
    PassFortTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            // Пример с обычным текстовым полем
            InputFieldWithCopy(
                value = "Пример текста",
                onValueChange = {},
                resourceString = R.string.passwordcreate_inputfield_login, // Замените на реальный ID ресурса
                isTitle = false
            )

            // Пример с заголовком
            InputFieldWithCopy(
                value = "Заголовок",
                onValueChange = {},
                resourceString = R.string.passwordcreate_inputfield_login, // Замените на реальный ID ресурса
                isTitle = true
            )

            // Пример с паролем
            InputFieldWithCopy(
                value = "secret123",
                onValueChange = {},
                resourceString = R.string.passwordcreate_inputfield_login, // Замените на реальный ID ресурса
                isPassword = true
            )

            // Пример только для чтения
            InputFieldWithCopy(
                value = "Только чтение",
                onValueChange = {},
                resourceString = R.string.passwordcreate_inputfield_login, // Замените на реальный ID ресурса
                isReadOnly = true
            )
        }
    }
}