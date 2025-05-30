package com.example.passfort.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PasswordRemindOptions(
    options: ImmutableList<String>,
    passwordIntervalDaysIndex: Int,
    enablePasswordChange: Boolean,
    setPasswordChange: () -> Unit,
    setChangeIntervalDaysCountIndex: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var label = stringResource(R.string.passwordcreate_toggle_remind_change_name)
        if (enablePasswordChange) {
            label += stringResource(R.string.passwordcreate_toggle_remind_change_name_adder)
        }
        ToggleLine(
            name = label,
            valueFlow = enablePasswordChange,
            horizontalPadding = 20.dp,
        ) {
            setPasswordChange()
        }
        if (enablePasswordChange) {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                SingleChoiceSegmentedButtonNew(
                    options = options,
                    selectedIndex = passwordIntervalDaysIndex,
                    switchAction = {setChangeIntervalDaysCountIndex(it)}
                )
            }
        }
    }
}

@Composable
fun ToggleLine(
    name: String,
    valueFlow: Boolean,
    horizontalPadding: Dp = 0.dp,
    toggleAction: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = toggleAction)
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(0.8f),
            fontSize = 16.sp,
            overflow = TextOverflow.Clip
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
