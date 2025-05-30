package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.ui.screen.passwordcreate.PartialBottomSheet

@Composable
fun AddPasswordScreen(navController: NavHostController, onAddPassword: () -> Unit) {
    Scaffold(
//        bottomBar = { NavigationBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            //PartialBottomSheet()
        }
    }
}