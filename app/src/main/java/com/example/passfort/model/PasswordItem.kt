package com.example.passfort.model

data class PasswordItem(
    val id: Int,              // Уникальный идентификатор
    val name: String,         // Название ресурса (например, "Gmail")
    val username: String,     // Логин пользователя
    val daysToExpire: Int,    // Количество дней до истечения пароля (0 или меньше — просрочен)
    val isCompromised: Boolean // Флаг, указывающий, что пароль скомпрометирован
)
