package com.example.passfort.designSystem.components

import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R

@Composable
fun InputFieldTitle(onValueChange: (String) -> Unit, onClick: () -> Unit) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        value = stringResource(R.string.passwordcreate_screen_title),
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 4.dp),
                onClick = onClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_button_edit),
                    contentDescription = "copy"
                )
            }
        }
    )
}

@Composable
fun InputFieldOutline(onValueChange: (String) -> Unit, resourceString: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
            .padding(horizontal = 25.dp),
        text = stringResource(resourceString),
        textAlign = TextAlign.Left,
        fontSize = 18.sp,
    )
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                RoundedCornerShape(15.dp)
            ),
        value = "",
        singleLine = true,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.outline,
            focusedBorderColor = Color.Transparent,
        )
    )
}

@Composable
fun InputFieldWithCopy(value: String,
                       onValueChange: (String) -> Unit,
                       resourceString: Int,
                       isTitle: Boolean = false,
                       isReadOnly : Boolean = false,
                       isPassword : Boolean = false,
                       isCopy: Boolean = false
){
    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
            .padding(horizontal = if (isTitle) 20.dp else 25.dp),
        text = stringResource(resourceString),
        textAlign = TextAlign.Left,
        fontSize = if (isTitle) 29.sp else 18.sp ,
        fontWeight = if (isTitle) FontWeight.Medium else FontWeight.Normal,
    )
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = if (isTitle) 16.dp else 0.dp)
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                RoundedCornerShape(15.dp)
            ),
        value = value,
        onValueChange = onValueChange,
        readOnly = isReadOnly,
        singleLine = !isTitle,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.outline,
        ),
        trailingIcon = {
            Row {
                if (isPassword) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_button_password_show),
                            contentDescription = "password show/hide"
                        )
                    }
                }
                if(isCopy){
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
            }
        }
    )
}

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (isPassword && !passwordVisible)
        PasswordVisualTransformation() else VisualTransformation.None

    val icon = if (passwordVisible)
        ImageVector.vectorResource(R.drawable.outline_visibility_24)
    else
        ImageVector.vectorResource(R.drawable.outline_visibility_off_24)

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            visualTransformation = visualTransformation,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                        )
                    }
                }
            } else null,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            modifier = modifier
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldPreview() {
    MaterialTheme {
        Column {
            InputFieldTitle({}, {})
            AuthTextField(
                label = "Логин",
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
            InputFieldOutline({}, 0)
            InputFieldWithCopy("pass", {}, 0)
        }
    }
}