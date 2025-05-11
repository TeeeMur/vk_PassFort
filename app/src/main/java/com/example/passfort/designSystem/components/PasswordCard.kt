package com.example.passfort.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.model.PasswordItem
import com.example.passfort.navigation.Screen

@Composable
fun PasswordCard(
    item: PasswordItem,
    navController: NavHostController,
    navigateString: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(navigateString) {
                    popUpTo(Screen.PasswordList.route) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.outline,
            contentColor = MaterialTheme.colorScheme.outline,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Text(
                    text = item.username,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Column(){
                IconButton(onClick = { /* Логика копирования */ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                        contentDescription = "Копировать",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewPasCard(){
    var pasData = PasswordItem(
        0,
        "Figma",
        "asdf@gmail.com",
        1,
        false)
    var navController = rememberNavController()
    PassFortTheme { PasswordCard(pasData, navController, "") }
}
