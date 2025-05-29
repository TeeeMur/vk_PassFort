package com.example.passfort.designSystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.viewModel.MainScreenViewModel

@Composable
fun RecentsList(
    @SuppressLint("ModifierParameter") titleModifier: Modifier = Modifier,
    viewModel: MainScreenViewModel,
    scrollEnabled: Boolean,
    listState: LazyListState,
    onClickPassword: (Long) -> Unit,
    title: String = "Недавние",
    onPin: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Left,
            modifier = titleModifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        val recents = viewModel.recentPasswords.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = scrollEnabled,
            state = listState,
        ) {
            items(recents.value) {
                item -> PasswordCard(
                item = item.convertToPasswordItem(),
                onClickPassword = onClickPassword,
                onPin = onPin,
                onDelete = onDelete,
            )
            }
        }
    }
}