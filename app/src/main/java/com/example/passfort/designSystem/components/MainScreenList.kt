package com.example.passfort.designSystem.components

import android.annotation.SuppressLint
import android.content.ClipData
import android.graphics.drawable.GradientDrawable
import android.os.PersistableBundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    //navController: NavController,
    navigationBar: @Composable () -> Unit = {PreviewNavBar()}) {
    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = navigationBar
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 60.dp)
                .scrollable(scrollState, Orientation.Vertical),
        ) {
            HorizontalUncontainedCarousel(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                state = rememberCarouselState{5},
                contentPadding = PaddingValues(horizontal = 32.dp),
                itemWidth = 280.dp,
                itemSpacing = 16.dp,
            ) { index ->
                Box(
                    modifier = Modifier.size(280.dp)
                        .fillMaxWidth()
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {Text("INDEX: $index")}
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.White)
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                SmallPasswordsList()
            }
        }
    }

}



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
            .background(Color.Gray)
            .padding(vertical = 16.dp, horizontal = 20.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Medium)
        LazyColumn(
            verticalArrangement = Arrangement.SpaceAround
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
        modifier = Modifier.fillMaxWidth(),
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