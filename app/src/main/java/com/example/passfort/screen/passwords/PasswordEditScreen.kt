package com.example.passfort.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.navigation.Screen
import com.example.passfort.viewModel.CreateViewModel
import com.yourpackage.ui.components.ButtonAdditionally

@Composable
fun PasswordEditScreen(
    viewModel: CreateViewModel = hiltViewModel(),
    onGeneratePassword: () -> Unit
) {

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
                PasswordRemindOptions(viewModel)
            }
            CreateBottomButtonLine(viewModel, onDismiss = {})
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

