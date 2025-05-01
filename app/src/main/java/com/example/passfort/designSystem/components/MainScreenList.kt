package com.example.passfort.designSystem.components

import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passfort.R

@Preview
@Composable
fun SmallPasswordsList(
    modifier: Modifier = Modifier,
    title: String = "Recently used",
    viewModel: MainScreenViewModel = hiltViewModel(),
    showIcons: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
            fontWeight = FontWeight.Medium)
        LazyColumn(
        ) {
            items(listOf(1, 2)) {
                SmallPasswordsListRow(viewModel)
            }
        }
    }
}

@Composable
fun SmallPasswordsListRow(viewModel: MainScreenViewModel) {
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("name")
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                val clipData = ClipData.newPlainText("Copied:", "value").apply {
                    description.extras = PersistableBundle().apply {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
                clipboardManager.setClip(ClipEntry(clipData))
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                contentDescription = "copy"
            )
        }
    }
}