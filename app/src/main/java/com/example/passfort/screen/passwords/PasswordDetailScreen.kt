package com.example.passfort.screen.passwords

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.passfort.R
import com.example.passfort.designSystem.components.AlertTwoButtonsDialog
import com.example.passfort.designSystem.components.BottomButtonLine
import com.example.passfort.designSystem.components.RectangleButton
import com.example.passfort.designSystem.components.InputFieldPassword
import com.example.passfort.designSystem.components.InputFieldTitle
import com.example.passfort.designSystem.components.InputFieldWithCopy
import com.example.passfort.designSystem.components.PasswordRemindOptions
import com.example.passfort.viewModel.DetailViewModel
import com.example.passfort.viewModel.PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS

@Composable
fun PasswordDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    idPasswordRecord: Long,
    onGeneratePassword: () -> Unit,
    onBackScreen: () -> Unit
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
            DetailScreen(
                viewModel = viewModel,
                onGeneratePassword = onGeneratePassword,
                onBackScreen = onBackScreen
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onGeneratePassword: () -> Unit,
    onBackScreen: () -> Unit
){
    val namePasswordDontChanged = viewModel.namePassword.value
    val openAlertDontSaveDialog = remember { mutableStateOf(false) }
    val openAlertDeleteDialog = remember { mutableStateOf(false) }

    Column {
        ButtonRows(
            onBackScreen = {
                if (viewModel.isChangedRecords.value) {
                    openAlertDontSaveDialog.value = true
                } else {
                    onBackScreen()
                }
            },
            onPinned = { viewModel.setPinnedState() },
            onDelete = { openAlertDeleteDialog.value = true }
        )
        ImageUserCard(
            imageCardUri = viewModel.imageCardUri.collectAsState().value,
            setUriImage = { viewModel.setImageUri(it) }
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
        RectangleButton(
            text = stringResource(R.string.passwordgen_generatebutton_text),
            paddingValues = PaddingValues(20.dp),
            onClick = onGeneratePassword
        )
        InputFieldWithCopy(
            labelResourceString = stringResource(R.string.passwordcreate_inputfield_note),
            value = viewModel.note.collectAsState().value,
            onValueChange = { viewModel.onNoteChange(it) },
        )
        PasswordRemindOptions(
            options = PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS,
            passwordIntervalDaysIndex = viewModel.changeIntervalDaysIndex.collectAsState().value,
            enablePasswordChange = viewModel.enablePasswordChange.collectAsState().value,
            setPasswordChange = { viewModel.setPasswordChange() },
            setChangeIntervalDaysCountIndex = { viewModel.setChangeIntervalDaysCountIndex(it) }
        )
    }
    BottomButtonLine(
        onDismiss = onBackScreen,
        onClick = { viewModel.editPassword() })

    when {
        openAlertDontSaveDialog.value -> {
            AlertTwoButtonsDialog(
                dialogText = stringResource(R.string.alert_dont_save_title),
                confirmButtonText = stringResource(R.string.alert_exit_button),
                dismissButtonText = stringResource(R.string.alert_exit_button),
                onConfirmation = {
                    openAlertDontSaveDialog.value = false
                    onBackScreen()
                },
                onDismissRequest = { openAlertDontSaveDialog.value = false }
            )
        }

        openAlertDeleteDialog.value -> {
            AlertTwoButtonsDialog(
                dialogText = StringBuilder()
                    .append(stringResource(R.string.alert_delete_title))
                    .append(" \"$namePasswordDontChanged\" ?")
                    .toString(),
                confirmButtonText = stringResource(R.string.alert_delete_button),
                dismissButtonText = stringResource(R.string.alert_cancel_button),
                onConfirmation = {
                    openAlertDeleteDialog.value = false
                    viewModel.deletePassword()
                    onBackScreen()
                },
                onDismissRequest = { openAlertDeleteDialog.value = false }
            )
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
) {
    DropdownMenu(
        modifier = Modifier
            .width(200.dp)
            .background(
                MaterialTheme.colorScheme.inverseOnSurface,
            ),
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Pinned") },
            onClick = {
                onPinned()
                onDismiss()
            }
        )
        HorizontalDivider()
        DropdownMenuItem(
            text = { Text(text = "Delete") },
            onClick = {
                onDismiss()
                onDelete()
            }
        )
    }
}

@Composable
fun ImageUserCard(
    imageCardUri: String,
    setUriImage: (String) -> Unit
) {
    val placeholder = painterResource(R.drawable.image_base_card_placeholder)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    if (imageCardUri != ""){
        selectedImageUri = imageCardUri.toUri()
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri:Uri? ->
        try {
            context.contentResolver.takePersistableUriPermission(
                uri!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
        catch (e: SecurityException){
            e.printStackTrace()
        }
        selectedImageUri = uri
        setUriImage(uri.toString())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            modifier = Modifier
                .size(120.dp),
            shape = RoundedCornerShape(22.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface),
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.SingleMimeType("image/*")
                    ))
            }
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(22.dp)),
                    model = ImageRequest.Builder(context)
                        .data(selectedImageUri!!)
                        .crossfade(true)
                        .build(),
                    error = placeholder,
                    contentDescription = stringResource(R.string.image_card_button),
                    contentScale = ContentScale.Crop,
                )
            }
            else{
                Icon(
                    imageVector = Icons.Outlined.AddPhotoAlternate,
                    tint = MaterialTheme.colorScheme.inverseSurface,
                    contentDescription = stringResource(R.string.image_card_button),
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


