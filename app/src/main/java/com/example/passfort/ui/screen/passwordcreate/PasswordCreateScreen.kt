package com.example.passfort.ui.screen.passwordcreate

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordLengthSlider
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.viewModel.GeneratorViewModel
import kotlinx.coroutines.launch

val horizontalPaddingValues = PaddingValues(
    horizontal = 20.dp
)

@Composable
@Preview
fun PasswordCreateScreen(viewModel: GeneratorViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            InputFieldWithCopy(viewModel.password.collectAsState().value)
            PasswordLengthSlider(viewModel)
            PasswordGenOptions(viewModel)
        }
        BottomButtonLine(viewModel)
    }
}

@Composable
fun PasswordGenOptions(viewModel: GeneratorViewModel) {
    ToggleLine(stringResource(R.string.passwordgen_toggle_digits_name), viewModel.enableDigits) {
        viewModel.setDigits()
    }
    ToggleLine(stringResource(R.string.passwordgen_toggle_lowercase_chars_name), viewModel.enableLowercaseCharacters) {
        viewModel.setLowercaseCharacters()
    }
    ToggleLine(stringResource(R.string.passwordgen_toggle_uppercase_chars_name), viewModel.enableUppercaseCharacters) {
        viewModel.setUppercaseCharacters()
    }
    ToggleLine(stringResource(R.string.passwordgen_toggle_specChar_name), viewModel.enableSpecSymbols) {
        viewModel.setSpecSymbols()
    }
}

@Composable
fun BottomButtonLine(viewModel: GeneratorViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontalPaddingValues)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .padding(end = 8.dp)
                .height(64.dp)
            ,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) { Text(
            text = stringResource(R.string.passwordgen_bottombutton_text),
            fontSize = 18.sp,
        )}
        OutlinedIconButton(
            modifier = Modifier.size(64.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(16.dp),
            onClick = { viewModel.generatePassword() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
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
                .fillMaxHeight(0.8f),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            PasswordCreateScreen()
        }
    }
}

