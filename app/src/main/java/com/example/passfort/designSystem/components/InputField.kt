package com.example.passfort.designSystem.components

import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import kotlinx.coroutines.flow.drop

@Composable
fun InputFieldTitle(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    placeholder: String = ""
) {
    TextField(
        modifier = modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        value = value,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        },
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            textDecoration = TextDecoration.Underline
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
fun InputFieldWithCopy(
    labelResourceString: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isTitle: Boolean = false,
    isSingleLine: Boolean = false,
    isReadOnly: Boolean = false,
    isCopy: Boolean = true,
    isShowErrorText: Boolean = false
) {
    val clipboardManager = LocalClipboard.current
    val clipData = ClipData.newPlainText("Copied:", value).apply {
        description.extras = PersistableBundle().apply {
            putBoolean("android.content.extra.IS_SENSITIVE", true)
        }
    }
    var stateCopy by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        InputFieldBase(
            labelResourceString = labelResourceString,
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            isTitle = isTitle,
            isSingleLine = isSingleLine,
            isReadOnly = isReadOnly,
            errorString = "Не должна быть пустой",
            isShowErrorText = (isShowErrorText && value == ""),
            trailingIcon = {
                if (isCopy) {
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = {
                        stateCopy = !stateCopy
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                        contentDescription = "copy"
                    )
                }
                }
            }
        )
    }
    LaunchedEffect(stateCopy) {
        snapshotFlow { stateCopy }
            .drop(1)
            .collect { clipboardManager.setClipEntry(ClipEntry(clipData)) }
    }
}

@Composable
fun InputFieldModalScreen(
    labelResourceString: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    onCLick: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isTitle: Boolean = false,
    isSingleLine: Boolean = false,
    isReadOnly: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        InputFieldBase(
            labelResourceString = labelResourceString,
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            isTitle = isTitle,
            isSingleLine = isSingleLine,
            isReadOnly = isReadOnly,
            titleFontSize = 20.sp,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = onCLick
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_next),
                        contentDescription = "apply"
                    )
                }
            }
        )
    }
}

@Composable
fun InputFieldPasswordWithCopy(
    labelResourceString: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    isTitle: Boolean = false,
    isReadOnly: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (!passwordVisible)
        PasswordVisualTransformation() else VisualTransformation.None

    val icon = if (passwordVisible)
        ImageVector.vectorResource(R.drawable.icon_button_password_hide)
    else
        ImageVector.vectorResource(R.drawable.icon_button_password_show)

    val clipboardManager = LocalClipboard.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        val clipData = ClipData.newPlainText("Copied:", value).apply {
            description.extras = PersistableBundle().apply {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }
        var copy = false
        InputFieldBase(
            labelResourceString = labelResourceString,
            value = value,
            onValueChange = onValueChange,
            isTitle = isTitle,
            isReadOnly = isReadOnly,
            visualTransformation = visualTransformation,
            trailingIcon = {
                Row {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription =
                                if (passwordVisible) stringResource(R.string.input_field_hide_password)
                                else stringResource(R.string.input_field_show_password)
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(end = 4.dp),
                        onClick = {
                            copy = !copy
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                            contentDescription = "copy"
                        )
                    }
                }
            }
        )
        LaunchedEffect(copy) {
            clipboardManager.setClipEntry(ClipEntry(clipData))
        }
    }
}

@Composable
fun InputFieldPassword(
    labelResourceString: String,
    value: String,
    isSingleLine: Boolean = false,
    onValueChange: (String) -> Unit = {},
                       errorString: String = "Не должны быть пустой",
                       isCopy: Boolean
) {

    var passwordVisible by remember { mutableStateOf(isCopy) }

    val visualTransformation = if (!passwordVisible)
        PasswordVisualTransformation() else VisualTransformation.None

    val icon = if (passwordVisible)
        ImageVector.vectorResource(R.drawable.icon_button_password_hide)
    else
        ImageVector.vectorResource(R.drawable.icon_button_password_show)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        InputFieldBase(
            labelResourceString = labelResourceString,
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            errorString = errorString,
            isSingleLine = isSingleLine,
            isShowErrorText = (value == ""),
            trailingIcon = {
                if (isCopy) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription =
                                if (passwordVisible) stringResource(R.string.input_field_hide_password)
                                else stringResource(R.string.input_field_show_password)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun InputFieldBase(
    labelResourceString: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isTitle: Boolean = false,
    isSingleLine: Boolean = false,
    isReadOnly: Boolean = false,
    enabled: Boolean = true,
    titleFontSize: TextUnit = 29.sp,
    errorString: String = "",
    isShowErrorText: Boolean = false,
    trailingIcon: @Composable (() -> Unit),
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, bottom = 8.dp)
                .padding(horizontal = if (isTitle) 0.dp else 5.dp),
            text = labelResourceString,
            textAlign = TextAlign.Left,
            fontSize = if (isTitle) titleFontSize else 18.sp,
            fontWeight = if (isTitle) FontWeight.Medium else FontWeight.Normal,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.inverseSurface,
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = if (isTitle) 16.dp else 0.dp)
                .padding(bottom = 5.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(15.dp)
                ),
            value = value,
            onValueChange = onValueChange,
            readOnly = isReadOnly,
            enabled = enabled,
            singleLine = isSingleLine,
            maxLines = 4,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(15.dp),
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = MaterialTheme.colorScheme.outline,
                disabledContainerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
        )

        if (errorString.isNotEmpty() && isShowErrorText) {
            Text(
                text = errorString,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(bottom = 10.dp)
                    .heightIn(max = 20.dp)
            )
        }
    }
}

@PreviewLightDark()
@Composable
fun AuthTextFieldPreview() {
    PassFortTheme {
        Column {
            InputFieldTitle(value = "New Password")
            InputFieldBase(
                labelResourceString = stringResource(R.string.passwordcreate_inputfield_password),
                value = "",
                onValueChange = {},
                trailingIcon = {}
            )
            InputFieldWithCopy(
                labelResourceString = "",
                value = "pass",
                onValueChange = {}
            )
            InputFieldPassword(
                labelResourceString = "",
                value = "pass",
                isCopy = false,
                onValueChange = {}
            )
            InputFieldPasswordWithCopy(
                labelResourceString = "",
                value = "pass",
                onValueChange = {}
            )
            InputFieldModalScreen(
                labelResourceString = "Dfdg",
                value = "pass",
                onValueChange = {},
                isTitle = true,

            )
        }
    }
}

@Composable
fun SettingsInputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    isPassword: Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.inverseSurface,
        ),
        decorationBox = { innerTextField ->
            if (value == "") {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.5f)
                )
            }
            innerTextField()
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.inverseSurface),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}