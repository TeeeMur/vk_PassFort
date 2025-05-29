package com.example.passfort.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.passfort.R
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.NavigationBar
import com.example.passfort.designSystem.components.RectangleButton
import com.example.passfort.designSystem.components.SettingsInputField
import com.example.passfort.designSystem.components.SingleChoiceSegmentedButtonNew
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.designSystem.theme.ChosenTheme
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
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
    onChangeTheme: (ChosenTheme) -> Unit,
    onGeneratePassword: () -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold(
        topBar = { UpperButtonLine(onBack = { navController.navigateUp() }) }
    ) { innerPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPaddingValues.calculateTopPadding())
        ) {
            InputFieldTitle(
                value = viewModel.name.collectAsState().value,
                onValueChange = { viewModel.updateName(it) }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                SettingsDataRow(
                    title = stringResource(R.string.settings_inputfield_title_email),
                    value = viewModel.email.collectAsState().value,
                    onChange = { viewModel.updateEmail(it) },
                )
                SettingsDataRow(
                    title = stringResource(R.string.settings_inputfield_title_password),
                    value = viewModel.password.collectAsState().value,
                    onChange = { viewModel.updatePassword(it) },
                    isPassword = true
                )
            }
            ToggleLine(
                name = stringResource(R.string.settings_toggle_sync_name),
                valueFlow = viewModel.syncEnabled.collectAsState().value
            ) {
                viewModel.changeSyncEnabled()
            }
            ToggleLine(
                name = stringResource(R.string.settings_toggle_notifications_name),
                valueFlow = viewModel.notificationsEnabled.collectAsState().value
            ) {
                viewModel.changeNotificationEnabled()
            }
            RectangleButton(stringResource(R.string.passwordgen_generatebutton_text)) {
                onGeneratePassword
            }
            Text(
                text = stringResource(R.string.settings_themechoice_title),
                modifier = Modifier.padding(start = 22.dp, bottom = 16.dp),
                fontSize = 22.sp,
            )
            SingleChoiceSegmentedButtonNew(
                options = viewModel.themeNames,
                selectedIndex = viewModel.themeIndex.collectAsState().value,
                switchAction = { viewModel.changeThemeIndex(it); onChangeTheme(ChosenTheme.entries[viewModel.themeIndex.value]) }
            )
            if (viewModel.dataChanged.collectAsState().value) {
                RectangleButton(stringResource(R.string.settings_savebutton_text)) {
                    viewModel.onSave()
                }
            }
        }
    }
}

@Composable
fun SettingsDataRow(
    title: String,
    value: String,
    onChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 14.dp)
        )
        SettingsInputField(
            value = value,
            onValueChange = onChange,
            isPassword = isPassword,
            placeholder = title
        )
    }
}

@Composable
fun UpperButtonLine(
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 14.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier
                .height(50.dp)
                .size(65.dp)
                .padding(10.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            onClick = onBack
        ) {
            Icon(
                modifier = Modifier
                    .width(15.dp)
                    .rotate(180f),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_next),
                tint = MaterialTheme.colorScheme.inverseSurface,
                contentDescription = null,
            )
        }
    }
}