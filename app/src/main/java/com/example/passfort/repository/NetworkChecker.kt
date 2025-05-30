package com.example.passfort.repository

interface NetworkChecker {
    fun isNetworkAvailable(): Boolean
}