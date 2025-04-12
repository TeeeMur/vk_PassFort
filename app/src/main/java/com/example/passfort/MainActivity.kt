package com.example.passfort

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.passfort.ui.screen.passwordgen.PasswordGenScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            PasswordGenScreen(clipboard)
        }
    }
}
