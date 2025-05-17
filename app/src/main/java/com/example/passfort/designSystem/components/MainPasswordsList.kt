package com.example.passfort.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MainPasswordsList(
    passwordsList: ImmutableList<PasswordRecordEntity>,
    title: String = "Недавние"
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
            modifier = Modifier.fillMaxWidth().padding(start = 4.dp)
        )
        passwordsList.forEach {
            item -> PasswordCard(item = item.convertToPasswordItem())
        }
    }
}