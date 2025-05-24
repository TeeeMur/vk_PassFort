package com.example.passfort.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object HomeScreen: Screen("home_screen")
    object PasswordList : Screen("password_list")
    object PasswordDetail : Screen("password_detail/{passwordId}") {
        fun createRoute(passwordId: Long) = "password_detail/$passwordId"
    }
    object PasswordGenerator : Screen("password_generator")
    object Settings : Screen("settings")
    object Profile : Screen("profile")
    object CreatePin : Screen("create_pin")
    object EnterPin : Screen("enter_pin")
}