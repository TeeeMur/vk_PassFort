package com.example.passfort.model

import java.util.Date

data class PasswordRecord(
    val name: String,
    val login: String,
    val password: String,
    val interval: Short,
    val lastSync: Date
)
