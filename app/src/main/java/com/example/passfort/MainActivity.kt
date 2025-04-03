package com.example.passfort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passfort.viewModel.PasswordsViewModel
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.PasswordsRepo
import com.example.passfort.repository.PasswordsRepoImpl
import com.example.passfort.viewModel.PasswordsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var db: PassFortDB
    @Inject
    lateinit var repo: PasswordsRepoImpl
    private val passwordsViewModel: PasswordsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            mainScreen(passwordsViewModel)
        }
    }
}

@Composable
@Preview
fun mainScreen(viewModel: PasswordsViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {
        Text("Hello!")
    }
}