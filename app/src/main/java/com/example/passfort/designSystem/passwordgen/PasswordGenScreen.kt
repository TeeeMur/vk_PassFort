package com.example.passfort.designSystem.passwordgen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.viewModel.PasswordGenViewModel

@Composable
@Preview
fun PasswordGenScreen(viewModel: PasswordGenViewModel = hiltViewModel()) {
    var generatedPassword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
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
                unfocusedBorderColor = Color.White
            ),
            trailingIcon = {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.content_copy_24),
                        contentDescription = "copy",
                        modifier = Modifier)
                }
            }
        )
        PasswordGenScreenParams(viewModel)
    }
}

@Composable
fun PasswordGenScreenParams(viewModel: PasswordGenViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var checked by remember { mutableStateOf(true) }
            Text(
                text = "Цифры",
                fontSize = 16.sp
                )
            Switch(
                checked = false,
                onCheckedChange = {checked = it}
            )
        }
    }
}