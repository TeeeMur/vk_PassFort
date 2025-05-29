package com.example.passfort.designSystem.components

import android.content.ClipData
import android.net.Uri
import android.os.PersistableBundle
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.GpsOff
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.model.PasswordItem
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@Composable
fun PasswordCard(
    item: PasswordItem,
    onClickPassword: (Long) -> Unit,
    onPin: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    val localClipboard = LocalClipboard.current
    val clipData = ClipData.newPlainText("Copied:", item.itemPassword).apply {
        description.extras = PersistableBundle().apply {
            putBoolean("android.content.extra.IS_SENSITIVE", true)
        }
    }
    var actionWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var stateCopy by remember { mutableStateOf(false) }

    val onHideProperties = {
        scope.launch {
            offset.animateTo(0f)
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged {
                    actionWidth = it.width.toFloat() + 20f
                }
                .height(IntrinsicSize.Min)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium)
                    .padding(horizontal = 15.dp),
                onClick = {
                    onPin(item.id)
                    onHideProperties()
                }
            ) {
                Icon(
                    imageVector = if(item.isPinned) Icons.Outlined.GpsOff else Icons.Outlined.PushPin,
                    contentDescription = "Pin",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.width(5.dp))
            IconButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
                    .padding(horizontal = 12.dp),
                onClick = {
                    onDelete(item.id)
                    onHideProperties()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .graphicsLayer {
                    translationX = offset.value
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { pointerId, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount).coerceIn(-actionWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            if (offset.value < -actionWidth / 2) {
                                scope.launch {
                                    offset.animateTo(-actionWidth)
                                }
                            } else {
                                onHideProperties()
                            }
                        }
                    )
                }
                .clickable { onClickPassword(item.id) },
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.outline,
                contentColor = MaterialTheme.colorScheme.outline,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageUserCard(item.imageCardUri)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.itemName,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                    Text(
                        text = item.itemLogin,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Column() {
                    IconButton(onClick = {
                        stateCopy = !stateCopy
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                            contentDescription = "Копировать",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
        LaunchedEffect(stateCopy) {
            snapshotFlow { stateCopy }
                .drop(1)
                .collect {
                    localClipboard.setClipEntry(ClipEntry(clipData))
                }
        }
    }
}

@Composable
fun ImageUserCard(
    imageCardUri: String,
) {
    val placeholder = painterResource(R.drawable.image_base_card_placeholder)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    placeholder.intrinsicSize

    if (imageCardUri != "") {
        selectedImageUri = imageCardUri.toUri()
    }

    Row(
        modifier = Modifier
            .width(75.dp)
            .height(65.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .aspectRatio(1f)
                .background(
                    MaterialTheme.colorScheme.onTertiary,
                    RoundedCornerShape(10.dp)
                    )
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                    RoundedCornerShape(10.dp)
                )
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedImageUri)
                        .crossfade(true)
                        .build(),
                    error = placeholder,
                    contentDescription = stringResource(R.string.image_card_button),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.image_base_card),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = stringResource(R.string.image_card_button),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewPasCard() {
    var pasData = PasswordItem(
        0,
        0,
        "Figma",
        "asdf@gmail.com",
        "",
        false,
        ""
    )
    PassFortTheme { PasswordCard(pasData, {}, {}, {}) }
}
