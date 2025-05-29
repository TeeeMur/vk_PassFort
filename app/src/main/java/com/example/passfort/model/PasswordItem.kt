package com.example.passfort.model

data class PasswordItem(
    val id: Long,
    val iconId: Int,
    val itemName: String,
    val itemLogin: String,
    val itemPassword: String,
    val isPinned: Boolean,
    val imageCardUri: String,
)
