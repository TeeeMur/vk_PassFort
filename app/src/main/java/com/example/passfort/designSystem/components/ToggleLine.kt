package com.example.passfort.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PasswordRemindOptions(
    passwordIntervalDays: Int,
    enablePasswordChange: Boolean,
    setPasswordChange: () -> Unit,
    setChangeIntervalDaysCount: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToggleLine(
            name = stringResource(R.string.passwordcreate_toggle_remind_change_name),
            valueFlow = enablePasswordChange
        ) {
            setPasswordChange()
        }

        if (enablePasswordChange) {
            SingleChoiceSegmentedButton(passwordIntervalDays) {
                setChangeIntervalDaysCount(it)
            }
        }
        else{
            setChangeIntervalDaysCount(0)
        }
    }
}

@Composable
fun ToggleLine(name: String, valueFlow: Boolean, toggleAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = toggleAction)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(start = 4.dp),
            fontSize = 16.sp
        )
        Switch(
            checked = valueFlow,
            onCheckedChange = { toggleAction() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.background,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.surface,
            ),
        )

    }
}
