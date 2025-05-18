package com.example.passfort.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R
import com.example.passfort.designSystem.components.BottomButtonLine
import com.example.passfort.designSystem.components.ButtonAdditionally
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordRemindOptions
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.viewModel.DetailViewModel

@Composable
fun PasswordDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    idPasswordRecord: Int,
    onGeneratePassword: () -> Unit,
    OnBackScreen: () -> Unit
) {

    viewModel.initPassword(idPasswordRecord)

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = 40.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                ButtonRows(
                    onBackScreen = OnBackScreen,
                    onPinned = { viewModel.setPinnedState() },
                    onDelete = { viewModel.deletePassword() }
                )
                InputFieldTitle(
                    value = viewModel.namePassword.collectAsState().value,
                    onValueChange = { viewModel.onNamePasswordChange(it) },
                    onClick = {}
                )
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_login),
                    value = viewModel.login.collectAsState().value,
                    onValueChange = { viewModel.onLoginChange(it) }
                )
                InputFieldPassword(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_password),
                    value = viewModel.password.collectAsState().value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                )
                ButtonAdditionally { onGeneratePassword() }
                InputFieldWithCopy(
                    labelResourceString = stringResource(R.string.passwordcreate_inputfield_note),
                    value = viewModel.note.collectAsState().value,
                    onValueChange = { viewModel.onNoteChange(it) },
                )
                PasswordRemindOptions(
                    passwordIntervalDays = viewModel.changeIntervalDays.collectAsState().value,
                    enablePasswordChange = viewModel.enablePasswordChange,
                    setPasswordChange = { viewModel.setPasswordChange() },
                    setChangeIntervalDaysCount = { viewModel.setChangeIntervalDaysCount(it) }
                )
            }
            BottomButtonLine({ viewModel.editPassword() }, onDismiss = OnBackScreen)
        }
    }
}

@Composable
fun ButtonRows(
    onBackScreen: () -> Unit,
    onPinned: () -> Unit,
    onDelete: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .size(65.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBackScreen()
                }
                .padding(10.dp)
                .background(
                    MaterialTheme.colorScheme.inverseOnSurface,
                    RoundedCornerShape(15.dp)
                )
                .rotate(180f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_next),
                tint = MaterialTheme.colorScheme.inverseSurface,
                contentDescription = null,
            )
        }
        Box(
            modifier = Modifier
                .height(52.dp)
                .size(65.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = !expanded
                }
                .padding(10.dp)
                .background(
                    MaterialTheme.colorScheme.inverseOnSurface,
                    RoundedCornerShape(15.dp)
                )
                .rotate(180f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_button_detail),
                tint = MaterialTheme.colorScheme.inverseSurface,
                contentDescription = null,
            )
            DropDownMenu(
                expanded = expanded,
                onPinned = onPinned,
                onDelete = onDelete,
                onDismiss = { expanded = false},
                onBackScreen = onBackScreen
                )
        }
    }
}

@Composable
fun DropDownMenu(
    expanded: Boolean,
    onPinned: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    onBackScreen: () -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .width(200.dp)
            .background(
                MaterialTheme.colorScheme.inverseOnSurface,
                //RoundedCornerShape(15.dp)
            ),
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Star") },
            onClick = {
                onPinned()
                onDismiss()
            }
        )
        HorizontalDivider()
        DropdownMenuItem(
            text = { Text(text = "Delete") },
            onClick = {
                onBackScreen()
                onDelete()
                //onDismiss()
            }
        )
    }
}

