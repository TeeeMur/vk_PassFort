package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.BottomButtonLine
import com.example.passfort.designSystem.components.RectangleButton
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordRemindOptions
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.CreateViewModel
import com.example.passfort.viewModel.PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS
import kotlinx.collections.immutable.toPersistentList

@Composable
fun PasswordCreateScreen(
    viewModel: CreateViewModel,
    onDismiss: () -> Unit,
    onGeneratePassword: () -> Unit) {
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
                    onValueChange = { viewModel.onPasswordChange(it) }
                )
                RectangleButton(
                    text = stringResource(R.string.passwordgen_generatebutton_text),
                    paddingValues = PaddingValues(20.dp),
                    onClick = onGeneratePassword
                )
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_note),
                    value = viewModel.note.collectAsState().value,
                    onValueChange = { viewModel.onNoteChange(it) },
                )
                PasswordRemindOptions(
                    passwordIntervalDaysIndex = viewModel.changeIntervalDaysIndex.collectAsState().value,
                    enablePasswordChange = viewModel.enablePasswordChange.collectAsState().value,
                    setPasswordChange = { viewModel.setPasswordChange() },
                    setChangeIntervalDaysCountIndex = { viewModel.setChangeIntervalDaysCountIndex(it) },
                    options = PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS.map { it -> "$it дней" }.toPersistentList()
                )
            }
        }
        BottomButtonLine({ viewModel.createPassword() }, onDismiss)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCreateModalScreen(
    viewModel: CreateViewModel = hiltViewModel(),
    showBottomSheetCreatePassword: Boolean,
    onDismiss: () -> Unit,) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showGenerateModalScreen by remember { mutableStateOf(false) }
    if (showBottomSheetCreatePassword) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            PasswordCreateScreen(
                viewModel = viewModel,
                onGeneratePassword = { showGenerateModalScreen = true },
                onDismiss = onDismiss
            )
        }
    }
    PasswordGenerateModalScreen(
        showBottomSheet = showGenerateModalScreen,
        viewModelEdit = viewModel,
        onDismiss = { showGenerateModalScreen = false }
    )
}

@PreviewLightDark()
@Composable
fun CreatePasswordPreview() {
    val viewModel = hiltViewModel<CreateViewModel>()
    PassFortTheme {
        PasswordCreateScreen(viewModel, {}, {})
    }
}

