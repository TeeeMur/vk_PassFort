package com.example.passfort.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.passfort.navigation.Screen

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)

fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Все пароли", Screen.PasswordList.route, Icons.Default.Lock),
        BottomNavItem("Добавить", Screen.AddPassword.route, Icons.Default.Add),
        BottomNavItem("Генератор", Screen.PasswordGenerator.route, Icons.Default.Build),
        BottomNavItem("Генератор", Screen.PasswordGenerator.route, Icons.Default.Settings)
    )
}