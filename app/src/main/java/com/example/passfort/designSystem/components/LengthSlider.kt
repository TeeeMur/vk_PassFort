package com.example.passfort.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.passfort.R
import com.example.passfort.ui.screen.passwordgen.horizontalPaddingValues
import com.example.passfort.viewModel.GeneratorViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.passwordgen_slider_title),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp),
            )

            Row() {
                Slider(
                    modifier = Modifier
                        .fillMaxWidth(0.75f),
                    value = lengthTextFieldValue.toFloat(),
                    onValueChange = { lengthTextFieldValue = it.toInt().toString() },
                    onValueChangeFinished = {
                        viewModel.setPasswordLength(lengthTextFieldValue.toInt())
                    },
                    track = { sliderState ->
                        SliderDefaults.Track(
                            modifier = Modifier
                                .scale(scaleX = 1f, scaleY = 1.8f),
                            sliderState = sliderState,
                        )
                    },
                    valueRange = 6f..128f
                )

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(50.dp)
                        .height(40.dp)
                        .padding(start = 30.dp)
                        .align(Alignment.CenterVertically)
                        .background(
                            color = colorResource(R.color.text_field_color),
                            RoundedCornerShape(20.dp)
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
                        unfocusedContainerColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.outline,
                    ),
                    singleLine = true,
                )

            }
        }
    }
}