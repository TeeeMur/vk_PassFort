package com.example.passfort.designSystem.components

import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.ui.screen.passwordgen.horizontalPaddingValues
import com.example.passfort.viewModel.GeneratorViewModel

@Preview
@Composable
fun InputField(onValueChange: (String) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
            .padding(horizontalPaddingValues),
        text = stringResource(R.string.passwordgen_screen_title),
        textAlign = TextAlign.Left,
        fontSize = 20.sp,
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
        value = "viewModel.password.collectAsState().value",
        onValueChange = onValueChange,
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
fun InputFieldWithCopy(value: String) {
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
        value = value,
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
                    val clipData = ClipData.newPlainText("Copied:", value).apply {
                        description.extras = PersistableBundle().apply {
                            putBoolean("android.content.extra.IS_SENSITIVE", true)
                        }
                    }
                    clipboardManager.setClip(ClipEntry(clipData))
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                    contentDescription = "copy"
                )
            }
        }
    )
}

@Composable
fun InputFieldPassword(){

}