package com.example.passfort.ui.screen.passwordgen

import android.content.ClipData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import android.os.PersistableBundle
//import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
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
import com.example.passfort.viewModel.GeneratorViewModel
import kotlinx.coroutines.flow.StateFlow

val horizontalPaddingValues = PaddingValues(
    horizontal = 20.dp
)

@Composable
@Preview
fun PasswordGenScreen(viewModel: GeneratorViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            TitleAndPasswordField(viewModel)
            PasswordLengthSlider(viewModel)
            PasswordGenOptions(viewModel)
        }
        BottomButtonLine(viewModel)
    }
}

@Composable
fun TitleAndPasswordField(viewModel: GeneratorViewModel) {
    val clipboardManager = LocalClipboardManager.current
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
            .padding(horizontalPaddingValues),
        text = stringResource(R.string.passwordgen_screen_title),
        textAlign = TextAlign.Left,
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium,
    )
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontalPaddingValues)
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.text_field_color),
                RoundedCornerShape(20.dp)
            ),
        value = viewModel.password.collectAsState().value,
        onValueChange = {},
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 4.dp),
                onClick = {
//                    val clipData = ClipData.newPlainText("Copied:", viewModel.password.value).apply {
//                        description.extras = PersistableBundle().apply {
//                            putBoolean("android.content.extra.IS_SENSITIVE", true)
//                        }
//                    }
                    //clipboardManager.setClip(ClipEntry(clipData))
                }
            ) {
//                Icon(
//                    Icons.Outlined.ContentCopy,
//                    contentDescription = "copy"
//                )
            }
        }
    )
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
fun PasswordLengthSlider(viewModel: GeneratorViewModel) {
    var lengthTextFieldValue by remember { mutableStateOf(viewModel.passwordLength.value.toString()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontalPaddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(end = 12.dp)
                .fillMaxWidth(0.84f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.passwordgen_slider_title),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp),
            )
            Slider(
                value = lengthTextFieldValue.toFloat(),
                onValueChange = { lengthTextFieldValue = it.toInt().toString() },
                onValueChangeFinished = {
                    viewModel.setPasswordLength(lengthTextFieldValue.toInt())
                },
                valueRange = 6f..128f,
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .height(48.dp)
                .background(
                    color = colorResource(R.color.text_field_color),
                    RoundedCornerShape(28.dp)
                ),
            value = lengthTextFieldValue,
            onValueChange = {
                if (it.isEmpty()) lengthTextFieldValue = it
                else if (it.isDigitsOnly()) {
                    if (it.toInt() in 1..128) {
                        lengthTextFieldValue = it
                        viewModel.setPasswordLength(it.toInt())
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
fun ToggleLine(name: String, valueFlow: StateFlow<Boolean>, toggleAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = toggleAction)
            .padding(horizontalPaddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(start = 4.dp),
            fontSize = 16.sp
        )
        Switch(
            checked = valueFlow.collectAsState().value,
            onCheckedChange = { toggleAction() }
        )
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
            border = BorderStroke(2.dp, Color.DarkGray),
            shape = RoundedCornerShape(16.dp),
            onClick = { viewModel.generatePassword() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

