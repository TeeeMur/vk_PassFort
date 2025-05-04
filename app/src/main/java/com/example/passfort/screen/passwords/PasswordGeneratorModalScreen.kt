package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldModalScreen
import com.example.passfort.designSystem.components.PasswordLengthSlider
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.CreateViewModel
import com.example.passfort.viewModel.GeneratorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGenerateModalScreen(showBottomSheet: Boolean, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            PasswordGeneratorModal() { onDismiss() }
        }
    }
}

@Composable
fun PasswordGeneratorModal(
    viewModelGeneration: GeneratorViewModel = hiltViewModel(),
    viewModelCreate: CreateViewModel = hiltViewModel(),
    onDismiss: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
    ) {
        InputFieldModalScreen(
            value = viewModelGeneration.password.collectAsState().value,
            onValueChange = {},
            onCLick = {
                viewModelCreate.onPasswordChange(viewModelGeneration.password.value)
                onDismiss()
            },
            labelResourceString = stringResource(R.string.passwordgen_screen_title),
            isTitle = true,
            isSingleLine = true,
            isReadOnly = true,
        )
        PasswordLengthSlider(viewModelGeneration)
        Spacer(Modifier.padding(bottom = 10.dp))
        PasswordGenOptions(viewModelGeneration)
    }
}

@PreviewLightDark
@Composable
fun GenerateModalScreenPreview(){
    PassFortTheme {
        PasswordGeneratorModal(onDismiss = {})
    }
}