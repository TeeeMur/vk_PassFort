package com.example.passfort.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.passfort.R
import com.example.passfort.designSystem.components.LeftTextButton
import com.example.passfort.designSystem.components.RectangleButton
import com.example.passfort.designSystem.components.SettingsInputField
import com.example.passfort.designSystem.components.SingleChoiceSegmentedButtonNew
import com.example.passfort.designSystem.components.ToggleLine
import com.example.passfort.designSystem.theme.ChosenTheme
import com.example.passfort.navigation.Screen
import com.example.passfort.viewModel.SettingsViewModel

@Composable
fun SettingsScreenNew(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
    onChangeTheme: (ChosenTheme) -> Unit,
    navBar: @Composable () -> Unit,
) {
    val horizontalPadding = 20.dp
    Scaffold(
        topBar = { SettingsTopBar(PaddingValues(start = horizontalPadding, end = horizontalPadding, top = 60.dp)) },
        bottomBar = navBar
    ) { innerPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPaddingValues.calculateTopPadding() + 12.dp, bottom = innerPaddingValues.calculateBottomPadding() + 18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(bottom = 12.dp)
            ) {
                SettingsDataRow(
                    title = stringResource(R.string.settings_inputfield_title_name),
                    value = viewModel.name.collectAsState().value,
                    onChange = { viewModel.updateName(it) },
                    placeholder = stringResource(R.string.settings_inputfield_placeholder_name)
                )
                SettingsDataRow(
                    title = stringResource(R.string.settings_inputfield_title_surname),
                    value = viewModel.surname.collectAsState().value,
                    onChange = { viewModel.updateSurname(it) },
                    placeholder = stringResource(R.string.settings_inputfield_placeholder_surname)
                )
                SettingsDataRow(
                    title = stringResource(R.string.settings_inputfield_title_email),
                    value = viewModel.email.collectAsState().value,
                    onChange = { viewModel.updateEmail(it) },
                    placeholder = stringResource(R.string.settings_inputfield_placeholder_email)
                )
            }
            ToggleLine(
                name = stringResource(R.string.settings_toggle_sync_name),
                valueFlow = viewModel.syncEnabled.collectAsState().value,
                horizontalPadding = horizontalPadding
            ) {
                viewModel.changeSyncEnabled()
            }
            ToggleLine(
                name = stringResource(R.string.settings_toggle_notifications_name),
                valueFlow = viewModel.notificationsEnabled.collectAsState().value,
                horizontalPadding = horizontalPadding
            ) {
                viewModel.changeNotificationEnabled()
            }
            Column(
                modifier = Modifier.padding(horizontal = horizontalPadding)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.settings_themechoice_title),
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 18.sp,
                )
                SingleChoiceSegmentedButtonNew(
                    options = viewModel.themeNames,
                    selectedIndex = viewModel.themeIndex.collectAsState().value,
                    switchAction = { viewModel.changeThemeIndex(it); onChangeTheme(ChosenTheme.entries[viewModel.themeIndex.value]) }
                )
                LeftTextButton(
                    text = "Расширенные параметры",
                    onClick = {navController.navigate(Screen.AdditionalSettings.route)},
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            if (viewModel.dataChanged.collectAsState().value) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    RectangleButton(
                        text = stringResource(R.string.settings_savebutton_text),
                        paddingValues = PaddingValues(horizontal = horizontalPadding, vertical = 12.dp),
                        onClick = { viewModel.onSave() }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsDataRow(
    title: String,
    placeholder: String,
    value: String,
    onChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        SettingsInputField(
            value = value,
            onValueChange = onChange,
            isPassword = isPassword,
            placeholder = placeholder,
        )
    }
}

@Composable
fun SettingsTopBar(
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(paddingValues)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.inverseSurface,
        )
    }
}
