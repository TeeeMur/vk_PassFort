package com.example.passfort.ui.screen.passwordcreate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldOutline
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.SingleChoiceSegmentedButton
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.viewModel.GeneratorViewModel

@Composable
@Preview
fun PasswordCreateScreen(viewModel: GeneratorViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val message = remember { mutableStateOf("") }

        Column {
            InputFieldTitle({}, {})
            InputFieldWithCopy(
                viewModel.password.collectAsState().value,
                onValueChange = {},
                R.string.passwordcreate_inputfield_login
            )
            InputFieldWithCopy(
                viewModel.password.collectAsState().value,
                onValueChange = {},
                R.string.passwordcreate_inputfield_password,
                isPassword = true
            )
            InputFieldOutline(
                { newText -> message.value = newText },
                R.string.passwordcreate_inputfield_note
            )
            PasswordRemindOptions(viewModel)
        }
        BottomButtonLine(viewModel)
    }
}

@Composable
fun PasswordRemindOptions(viewModel: GeneratorViewModel) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToggleLine(
            stringResource(R.string.passwordcreate_toggle_remind_change_name),
            viewModel.enableDigits
        ) {
            viewModel.setDigits()
        }
        SingleChoiceSegmentedButton()
    }
}

@Composable
fun BottomButtonLine(viewModel: GeneratorViewModel) {
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
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.passwordcreate_bottombutton_save),
                fontSize = 18.sp,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(showBottomSheet: Boolean, onDismiss: () -> Unit) {
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
            PasswordCreateScreen()
        }
    }
}

