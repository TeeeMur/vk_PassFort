package com.example.passfort.model

data class PasswordItem(
    val id: Int,              // Уникальный идентификатор
    val name: String,         // Название ресурса (например, "Gmail")
    val username: String,     // Логин пользователя
)
