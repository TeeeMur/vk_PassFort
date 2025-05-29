package com.example.passfort.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    width: Dp = 56.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    textColor: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = Color.Transparent
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .width(width),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        contentPadding = contentPadding,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    width: Dp = 56.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    fillMaxWidth: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier
            .then(
                if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier.width(width)
            )
            .height(height)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
        } else {
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview_Light() {
    PassFortTheme {
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SecondaryButton(
                text = "Создать аккаунт",
                onClick = {}
            )

            SecondaryButton(
                text = "Другая кнопка",
                onClick = {},
                textColor = Color.Red,
                containerColor = Color.LightGray
            )

            AuthButton(
                text = "войти",
                onClick = {},
                isLoading = false,
                modifier = Modifier
                    .fillMaxWidth(),
                fillMaxWidth = true
            )

            AuthButton(
                text = "Зарегистрироваться",
                onClick = {},
                isLoading = false,
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                contentColor = MaterialTheme.colorScheme.surface,
                fillMaxWidth = true
            )
        }
    }
}

@Composable
fun RectangleButton(
    text: String,
    onClick: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.inversePrimary,
            fontSize = 18.sp,
        )
    }
}

@Composable
fun BottomButtonLine(
    onClick: () -> Boolean,
    onDismiss: () -> Unit
) {
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
            onClick = {
                if (onClick())
                    onDismiss()
            }
        ) {
            Text(
                text = stringResource(R.string.passwordcreate_bottombutton_save),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 18.sp,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ButtonsPreview(){
    PassFortTheme {
        Column {
            RectangleButton(stringResource(R.string.passwordgen_generatebutton_text)) {}
            BottomButtonLine({ true }, {})
        }
    }
}