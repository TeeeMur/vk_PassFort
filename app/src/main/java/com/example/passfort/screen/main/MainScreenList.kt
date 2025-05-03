package com.example.passfort.screen.main

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.passfort.R
import com.example.passfort.viewModel.MainScreenViewModel
import com.example.passfort.designSystem.components.PreviewNavBar
import com.example.passfort.designSystem.components.SearchBar
import com.example.passfort.model.dbentity.PasswordRecordEntity


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController,
    navigationBar: @Composable () -> Unit = { PreviewNavBar() }
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState { 5 }
    Scaffold(
        bottomBar = navigationBar
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 60.dp)
                .scrollable(scrollState, Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
            ) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .border(8.dp, Color.Blue, RoundedCornerShape(16.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) { Text("INDEX: $index", fontSize = 36.sp) }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { index ->
                    val backColor = if (index == pagerState.currentPage) MaterialTheme.colorScheme.surface
                        else MaterialTheme.colorScheme.secondary
                    Box(
                        modifier = Modifier.padding(horizontal = 4.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(backColor)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 14.dp, vertical = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var searchString by remember{mutableStateOf("")}
                SearchBar(value = searchString,
                    placeholder = "Начните искать свой пароль",
                    modifier = Modifier.padding(vertical = 6.dp)) {searchString = it}
                SmallPasswordsList(
                    modifier = Modifier.padding(vertical = 6.dp),
                    title = "Недавние",
                    passwordsList = viewModel.recentPasswords.collectAsState().value)
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SmallPasswordsList(modifier = Modifier.weight(1f).padding(end=12.dp),
                        title = "Закрепленные", passwordsList = viewModel.pinnedPasswords.collectAsState().value)
                    SmallPasswordsList(modifier = Modifier.weight(1f),
                        passwordsList = viewModel.thirdPasswords.collectAsState().value)
                }
            }
        }
    }

}

@Composable
fun SmallPasswordsList(
    modifier: Modifier = Modifier,
    title: String = "Recently used",
    passwordsList: List<PasswordRecordEntity>,
    showIcons: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .width(560.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier,
            fontWeight = FontWeight.Medium
        )
        if (passwordsList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                items(passwordsList) {
                    SmallPasswordsListRow(it)
                }
            }
        }
    }
}

@Composable
fun SmallPasswordsListRow(item: PasswordRecordEntity) {
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.passwordRecordName,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                val clipData = ClipData.newPlainText("Copied:", item.passwordRecordPassword).apply {
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