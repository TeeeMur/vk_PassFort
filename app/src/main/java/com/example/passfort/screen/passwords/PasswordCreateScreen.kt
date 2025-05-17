package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.SingleChoiceSegmentedButton
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.CreateViewModel
import com.example.passfort.designSystem.components.ButtonAdditionally

@Composable
fun PasswordCreateScreen(viewModel: CreateViewModel = hiltViewModel(), onDismiss: () -> Unit, onGeneratePassword: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        InputFieldTitle(
            value = viewModel.namePassword.collectAsState().value,
            onValueChange = { viewModel.onNamePasswordChange(it) },
            onClick = {}
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            item {
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_login),
                    value = viewModel.login.collectAsState().value,
                    onValueChange = { viewModel.onLoginChange(it) },
                    isShowErrorText = viewModel.isEmptyRecords.collectAsState().value,
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
        }
        CreateBottomButtonLine(viewModel, onDismiss)
    }
}

@Composable
fun PasswordRemindOptions(viewModel: CreateViewModel)
{
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToggleLine(
            name = stringResource(R.string.passwordcreate_toggle_remind_change_name),
            valueFlow = viewModel.enablePasswordChange
        ) {
            viewModel.setPasswordChange()
        }
        
        if (viewModel.enablePasswordChange.collectAsState().value) {
            SingleChoiceSegmentedButton() {
                viewModel.setChangeIntervalDaysCount(it)
            }
        }
    }
}

@Composable
fun CreateBottomButtonLine(
    viewModel: CreateViewModel,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(64.dp),
            shape = RoundedCornerShape(50.dp),
            onClick = {
                if (viewModel.createPassword())
                    onDismiss()
            }
        ) {
            Text(
                text = stringResource(R.string.passwordcreate_bottombutton_save),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 18.sp,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCreateModalScreen(showBottomSheet: Boolean, onDismiss: () -> Unit, onGeneratePassword: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            PasswordCreateScreen(
                onGeneratePassword = onGeneratePassword,
                onDismiss = onDismiss
            )
        }
    }
}

@PreviewLightDark()
@Composable
fun CreatePasswordPreview() {
    val viewModel = hiltViewModel<CreateViewModel>()
    PassFortTheme {
        PasswordCreateScreen(viewModel, {}, {})
    }
}

