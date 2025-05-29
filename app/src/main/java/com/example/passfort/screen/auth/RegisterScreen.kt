package com.example.passfort.screen.auth

import CenteredErrorDialog
import com.example.passfort.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.passfort.designSystem.components.AuthButton
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.ui.register.RegisterUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterAttempt: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onErrorDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showErrorDialog = uiState.generalError != null

    Column(
        modifier = modifier
            .clipToBounds()
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
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
                .weight(2f)
                .background(MaterialTheme.colorScheme.primary)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 16.dp)
        ) {
            RegisterForm(
                uiState = uiState,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                onRegisterAttempt = onRegisterAttempt,
                onPrivacyPolicy = onPrivacyPolicy

            )
        }
        if (showErrorDialog) {
            CenteredErrorDialog(
                title = stringResource(R.string.error_dialog_title),
                errorMessage = uiState.generalError ?: "",
                onDismiss = onErrorDialogDismiss
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
    onPrivacyPolicy: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {

        InputFieldWithCopy(
            labelResourceString = stringResource(R.string.register_name_label),
            value = uiState.name,
            onValueChange = onNameChange,
            isReadOnly = uiState.isLoading,
            isCopy = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputFieldWithCopy(
            labelResourceString = stringResource(R.string.register_email_label),
            value = uiState.email,
            onValueChange = onEmailChange,
            isReadOnly = uiState.isLoading,
            isCopy = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputFieldPassword(
            labelResourceString = stringResource(R.string.register_password_label),
            value = uiState.password,
            onValueChange = onPasswordChange,
            errorString = "",
            isCopy = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputFieldPassword(
            labelResourceString = stringResource(R.string.register_confirm_password_label),
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            errorString = "",
            isCopy = false
        )

        if (uiState.confirmPassword.isNotEmpty() && uiState.confirmPasswordError == null && uiState.passwordError == null) {
            val isMatch = uiState.password == uiState.confirmPassword
            Text(
                text = if (isMatch) "Пароли совпадают" else "Пароли не совпадают",
                color = if (isMatch) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, top = 4.dp, bottom = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        AuthButton(
            text = stringResource(R.string.register_button),
            onClick = onRegisterAttempt,
            isLoading = uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            fillMaxWidth = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.login_privacy_policy_button),
            style = MaterialTheme.typography.labelSmall.copy(
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onPrivacyPolicy),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}



