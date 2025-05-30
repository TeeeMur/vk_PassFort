package com.example.passfort.repository

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun login(username: String, password: String): Result<Unit>
}