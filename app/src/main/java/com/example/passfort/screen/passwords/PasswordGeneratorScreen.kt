package com.example.passfort.screen.passwords

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.designSystem.components.PasswordLengthSlider
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.viewModel.GeneratorViewModel


@Composable
fun PasswordGeneratorScreen(navController: NavHostController, onAddPassword: () -> Unit) {
    Scaffold(
        bottomBar = { NavigationBar(navController, onAddPassword) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            PasswordGenScreen()
        }
    }
}

@Composable
@Preview
fun PasswordGenScreen(viewModel: GeneratorViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            InputFieldWithCopy(
                value = viewModel.password.collectAsState().value,
                onValueChange = {},
                labelResourceString = stringResource(R.string.passwordgen_screen_title),
                isTitle = true,
                isReadOnly = true
            )
            PasswordLengthSlider(viewModel)
            Spacer(Modifier.padding(bottom = 10.dp))
            PasswordGenOptions(viewModel)
        }
        GenerateBottomButtonLine(viewModel)
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
fun GenerateBottomButtonLine(viewModel: GeneratorViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .padding(end = 8.dp)
                .height(56.dp)
            ,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) { Text(
            text = stringResource(R.string.passwordgen_bottombutton_text),
            color = MaterialTheme.colorScheme.inversePrimary,
            fontSize = 18.sp,
        )}
        OutlinedIconButton(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary),
            onClick = { viewModel.generatePassword() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                tint = MaterialTheme.colorScheme.inversePrimary,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}