package com.example.passfort.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.passfort.R
import com.example.passfort.designSystem.components.LeftTextButton

@Composable
fun AdditionalSettings(
    onLogout: () -> Unit,
    onDeleteProfile: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = { UpperButtonLine { onBack() }}
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LeftTextButton(
                    text = "Документы",
                    onClick = {},
                    modifier = Modifier.padding(vertical = 6.dp)
                )
                LeftTextButton(
                    text = "Выйти из профиля",
                    onClick = onLogout,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            LeftTextButton(
                text = "Удалить профиль",
                onClick = onDeleteProfile,
                modifier = Modifier.padding(vertical = 4.dp),
                mainColor = Color.Red,
                textColor = Color.White
            )
        }
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