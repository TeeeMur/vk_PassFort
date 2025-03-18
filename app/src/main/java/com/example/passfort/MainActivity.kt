package com.example.passfort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.passfort.viewModels.PassFortViewModel
import com.example.passfort.model.Dependencies

class MainActivity : ComponentActivity() {

    private val passFortViewModel by lazy { PassFortViewModel(Dependencies.passwordRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            mainScreen(passFortViewModel)
        }
    }
}

@Composable
fun mainScreen(viewModel: PassFortViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val localContext = LocalContext.current
        var name by remember{mutableStateOf("")}
        var login by remember{mutableStateOf("")}
        var password by remember{mutableStateOf("")}
        OutlinedTextField(value = name, onValueChange = {name = it}, label = { Text("name") })
        OutlinedTextField(value = login, onValueChange = {login = it}, label = { Text("login") })
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text("password") })
        Button(onClick = { viewModel.getPasswordByName(name)
            name = viewModel.currPassword.value?.passwordAccountName ?: ""
            login = viewModel.currPassword.value?.passwordAccountLogin ?: ""
            password = viewModel.currPassword.value?.passwordAccountPassword ?: ""}) {
            Text(text = "Get record!")
        }
        Button(onClick = {
            viewModel.insertNewPassword(name, login, password)
        }) {
            Text(text = "Set record!")
        }
    }
}