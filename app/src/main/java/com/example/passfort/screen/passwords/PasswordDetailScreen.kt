package com.example.passfort.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordRemindOptions
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.DetailViewModel
import com.yourpackage.ui.components.ButtonAdditionally
import com.yourpackage.ui.components.BottomButtonLine

@Composable
fun PasswordDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    idPasswordRecord: Int,
    onGeneratePassword: () -> Unit
) {

    viewModel.initPassword(idPasswordRecord)

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = 40.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Row {


                }
                InputFieldTitle(
                    value = viewModel.namePassword.collectAsState().value,
                    onValueChange = { viewModel.onNamePasswordChange(it) },
                    onClick = {}
                )
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_login),
                    value = viewModel.login.collectAsState().value,
                    onValueChange = { viewModel.onLoginChange(it) }
                )
                InputFieldPassword(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_password),
                    value = viewModel.password.collectAsState().value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                )
                ButtonAdditionally { onGeneratePassword() }
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_note),
                    value = viewModel.note.collectAsState().value,
                    onValueChange = { viewModel.onNoteChange(it) },
                )
                PasswordRemindOptions(
                    enablePasswordChange = viewModel.enablePasswordChange,
                    setPasswordChange = { viewModel.setPasswordChange() },
                    setChangeIntervalDaysCount = { viewModel.setChangeIntervalDaysCount(it) }
                )
            }
            BottomButtonLine({ viewModel.editPassword() }, onDismiss = {})
        }
    }
}

@PreviewLightDark()
@Composable
fun EditPasswordPreview() {
    //val viewModel = hiltViewModel<CreateViewModel>()
    PassFortTheme {
        Row {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clickable(
                    ) {

                    }
                    .padding(10.dp)
                    .background(MaterialTheme.colorScheme.inverseOnSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_next),
                    tint = MaterialTheme.colorScheme.inverseSurface,
                    contentDescription = null,
                )
            }
        }
    }
}

