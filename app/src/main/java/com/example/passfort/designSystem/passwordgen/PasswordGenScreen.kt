package com.example.passfort.designSystem.passwordgen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.viewModel.PasswordGenViewModel

@Composable
@Preview
fun PasswordGenScreen(viewModel: PasswordGenViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            TitleWithPasswordField(viewModel)
            GenPasswordLengthSlider(viewModel)
            PasswordGenScreenParams(viewModel)
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                onClick = {}
            ) { Text("Записать пароль")}
            Button(onClick = {}) { Icon(Icons.Outlined.Refresh, contentDescription = "")}
        }
    }
}

@Composable
fun TitleWithPasswordField(viewModel: PasswordGenViewModel) {
    var generatedPassword by remember { mutableStateOf("") }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp),
        text = stringResource(R.string.passwordgen_screen_title),
        textAlign = TextAlign.Left,
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium,
    )
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.text_field_color),
                RoundedCornerShape(20.dp)
            ),
        value = generatedPassword,
        onValueChange = {generatedPassword = it},
        singleLine = true,
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 4.dp),
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(R.drawable.content_copy_24),
                    contentDescription = "copy",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Composable
fun PasswordGenScreenParams(viewModel: PasswordGenViewModel) {
    ToggleLine(stringResource(R.string.passwordgen_toggle_digits_name), viewModel)
    ToggleLine(stringResource(R.string.passwordgen_toggle_chars_name), viewModel)
    ToggleLine(stringResource(R.string.passwordgen_toggle_specChar_name), viewModel)
}

@Composable
fun GenPasswordLengthSlider(viewModel: PasswordGenViewModel) {
    var generatedPasswordLength by remember { mutableIntStateOf(64) }
    var lengthTextFieldValue by remember { mutableStateOf(generatedPasswordLength.toString()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.passwordgen_slider_title),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp),
            )
            Slider(
                modifier = Modifier
                    .fillMaxWidth(0.84f),
                value = generatedPasswordLength.toFloat(),
                onValueChange = { generatedPasswordLength = it.toInt()
                                lengthTextFieldValue = generatedPasswordLength.toString()},
                valueRange = 6f..128f,
            )
        }
        OutlinedTextField(
            modifier = Modifier.background(
                color = colorResource(R.color.text_field_color),
                RoundedCornerShape(28.dp)
            ),
            value = lengthTextFieldValue,
            onValueChange = {
                if (it.isEmpty()) lengthTextFieldValue = it
                else if (it.isDigitsOnly()) {
                    if (it.toInt() in 1..128) {
                        lengthTextFieldValue = it
                        generatedPasswordLength = it.toInt()
                    }
                }},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
            ),
            singleLine = true,
        )
    }
}

@Composable
fun ToggleLine(name: String, viewModel: PasswordGenViewModel) {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clickable(onClick = { checked = !checked }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = {checked = it}
        )
    }
}

