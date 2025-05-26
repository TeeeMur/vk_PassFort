package com.example.passfort.screen.passwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.designSystem.components.SettingsInputField
import com.example.passfort.navigation.Screen
import com.example.passfort.viewModel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    onLogout: () -> Unit,
    onAddPassword: () -> Unit
) {
    Scaffold(
        bottomBar = { NavigationBar(navController, onAddPassword) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "PasswordGeneratorScreen",
            )
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Выйти из аккаунта")
            }
        }
    }
}

@Composable
fun SettingsScreenNew(
    viewModel: SettingsViewModel,
    navController: NavHostController,
    onLogout: () -> Unit,
    onAddPassword: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            SettingsDataRow(
                title = "Имя",
                value = viewModel.name.collectAsState().value,
                onChange = {viewModel.updateName(it)},
                onSave = {},
                showIcon = true
            )
        }
    }
}

@Composable
fun SettingsDataRow(
    title: String,
    value: String,
    onChange: (String) -> Unit,
    onSave: () -> Unit,
    showIcon: Boolean
) {
    Column() {
        Text(
            text = title,
            color = Color.LightGray
        )
        SettingsInputField(
            value = value,
            onValueChange = onChange,
            onClick = onSave,
            showIcon = showIcon
        )
    }
}