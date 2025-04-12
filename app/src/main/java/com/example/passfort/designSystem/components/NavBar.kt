package com.example.passfort.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.passfort.R
import com.example.passfort.navigation.Screen
import com.google.firebase.annotations.PreviewApi

data class NavigationBarItem(
    val nameOpenActivity: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun NavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavigationBarItem(
            nameOpenActivity = Screen.PasswordList.route,
            selectedIcon = ImageVector.vectorResource(R.drawable.navbar_home_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.navbar_home_unselected),
        ),
        NavigationBarItem(
            nameOpenActivity = Screen.AddPassword.route,
            selectedIcon = ImageVector.vectorResource(R.drawable.navbar_passwords_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.navbar_passwords_unselected)
        ),
        NavigationBarItem(
            nameOpenActivity = Screen.PasswordGenerator.route,
            selectedIcon = ImageVector.vectorResource(R.drawable.navbar_key_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.navbar_key_unselected)
        ),
        NavigationBarItem(
            nameOpenActivity = Screen.Settings.route,
            selectedIcon = ImageVector.vectorResource(R.drawable.navbar_settings_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.navbar_settings_unselected)
        )
    )

    val selectedItemByIndex by remember {
        mutableStateOf(0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(40.dp))
            .height(80.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEachIndexed { index, item ->
            NavItem(item.selectedIcon, item.unselectedIcon, index == selectedItemByIndex)
            {
                navController.navigate(item.nameOpenActivity) {
                    popUpTo(Screen.PasswordList.route) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }

            }
            if (index == 1) {

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = -60) }
                        .clickable { navController.navigate(Screen.AddPassword.route) }
                        .size(80.dp)
                        .align(Alignment.Bottom)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(35.dp)
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize(),
                            imageVector = ImageVector.vectorResource(R.drawable.icon_button_add),
                            tint = MaterialTheme.colorScheme.surface,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(
    iconSelected: ImageVector,
    iconUnselected: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconImage = if (isSelected) iconSelected else iconUnselected
    val iconColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = iconImage,
            tint = iconColor,
            contentDescription = null,
        )
    }
}