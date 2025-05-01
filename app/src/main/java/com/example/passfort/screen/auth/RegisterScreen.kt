package com.example.passfort.screen.auth

import RegisterUiState
import com.example.passfort.R
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordField
import com.yourpackage.ui.components.AuthButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterAttempt: () -> Unit,
    onBack: () -> Unit,
    onPrivacyPolicy: () -> Unit,
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
                .weight(3f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            RegisterForm(
                uiState = uiState,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                onRegisterAttempt = onRegisterAttempt,
                onBack = onBack,
                onPrivacyPolicy = onPrivacyPolicy

            )
        }
    }
}


@Composable
fun RegisterForm(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterAttempt: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onBack: () -> Unit
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        InputFieldWithCopy(
            value = uiState.name,
            onValueChange = onNameChange,
            resourceString = R.string.register_name_label,
            isReadOnly = uiState.isLoading,
            isPassword = false,
            isCopy = false,
            isLoginScreen = true
        )
        uiState.nameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(horizontal = 25.dp),
            )
        }
        Spacer(Modifier.height(8.dp))
        InputFieldWithCopy(
            value = uiState.email,
            onValueChange = onEmailChange,
            resourceString = R.string.register_email_label,
            isReadOnly = uiState.isLoading,
            isPassword = false,
            isCopy = false,
            isLoginScreen = true
        )
        uiState.emailError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(horizontal = 25.dp),
            )
        }
        Spacer(Modifier.height(8.dp))
        PasswordField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            labelResId = R.string.register_password_label,
            isEnabled = !uiState.isLoading
        )
        uiState.passwordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(horizontal = 25.dp),
            )
        }
        Spacer(Modifier.height(8.dp))
        PasswordField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            labelResId = R.string.register_confirm_password_label,
            isEnabled = !uiState.isLoading
        )

        if (uiState.confirmPassword.isNotEmpty()) {
            val isMatch = uiState.password == uiState.confirmPassword
            Text(
                text = if (isMatch) "Пароли совпадают" else "Пароли не совпадают",
                color = if (isMatch) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(horizontal = 25.dp),
            )
        }

        Spacer(Modifier.height(24.dp))

        AuthButton(
            text = stringResource(R.string.register_button),
            onClick = onRegisterAttempt,
            modifier = Modifier
                .fillMaxWidth(),
            isLoading = uiState.isLoading,
            fillMaxWidth = true
        )

        Text(
            text = stringResource(R.string.register_privacy_policy),
            style = MaterialTheme.typography.labelSmall.copy(
                textDecoration = TextDecoration.Underline
            ),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clickable { onPrivacyPolicy() }
                .align(Alignment.CenterHorizontally)
        )
    }
}


