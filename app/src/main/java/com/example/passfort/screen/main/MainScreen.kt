package com.example.passfort.screen.main

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.passfort.R
import com.example.passfort.designSystem.components.MainPasswordsList
import com.example.passfort.designSystem.components.PreviewNavBar
import com.example.passfort.designSystem.components.SearchBar
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.viewModel.MainScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt

enum class Anchors {
    Start,
    End
}

val mainScreenImages = persistentListOf(
    R.drawable.mainscreen_image1,
    R.drawable.mainscreen_image2,
    R.drawable.mainscreen_image3,
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController,
    navigationBar: @Composable () -> Unit = {
        PreviewNavBar()
    }
) {
    val pagerState = rememberPagerState { mainScreenImages.size }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(end = 16.dp),
                title = {
                    Text(
                        text = "Привет, Арина!",
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = stringResource(R.string.profile_icon_string_description),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        bottomBar = navigationBar,
    ) { scaffoldPaddingValues ->
        val anchors = DraggableAnchors<Anchors> {
            Anchors.Start at 0F
            Anchors.End at -800F
        }
        val anchoredDraggableState = remember {
            AnchoredDraggableState(
                initialValue = Anchors.Start,
                anchors = anchors
            )
        }
        val backgroundColorAlpha =
            (1 - anchoredDraggableState.requireOffset().absoluteValue / anchoredDraggableState.anchors.minPosition().absoluteValue).pow(
                0.85F
            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(
                    align = Alignment.Top,
                    unbounded = true
                )
                .background(MaterialTheme.colorScheme.primary.copy(alpha = backgroundColorAlpha))
                .padding(top = scaffoldPaddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        anchoredDraggableState.updateAnchors(
                            DraggableAnchors<Anchors> {
                                Anchors.Start at 0F
                                Anchors.End at -it.size.height.toFloat()
                            }
                        )
                    },
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp), // hardcode hooray!!! (maximum height of images + ~30 dp)
                    state = pagerState,
                ) { index ->
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        imageVector = ImageVector.vectorResource(mainScreenImages[index]),
                        contentDescription = null,
                        alpha = backgroundColorAlpha
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(mainScreenImages.size) { index ->
                        val backColor =
                            if (index == pagerState.currentPage) MaterialTheme.colorScheme.surface
                            else MaterialTheme.colorScheme.secondary
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(backColor)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = 0,
                            y = anchoredDraggableState.requireOffset().roundToInt()
                        )
                    }
                    .anchoredDraggable(
                        state = anchoredDraggableState,
                        orientation = Orientation.Vertical
                    )
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(
                        LocalWindowInfo.current.containerSize.height.dp
                                - scaffoldPaddingValues.calculateBottomPadding() * 1.5F
                                - 12.dp
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var searchString by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )
                SearchBar(
                    value = searchString,
                    placeholder = stringResource(R.string.search_passwords_field_placeholder),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) { searchString = it }
                SmallPasswordsList(
                    modifier = Modifier.padding(vertical = 6.dp),
                    title = stringResource(R.string.main_screen_pinned_title),
                    passwordsList = viewModel.pinnedPasswords.collectAsState().value.toImmutableList(),
                    showIcons = true
                )
                if (viewModel.recentPasswords.value.isNotEmpty()) {
                    MainPasswordsList(
                        passwordsList = viewModel.recentPasswords.collectAsState().value.toImmutableList(),
                    )
                }
            }
        }
    }
}

@Composable
fun SmallPasswordsList(
    modifier: Modifier = Modifier,
    passwordsList: ImmutableList<PasswordRecordEntity>,
    title: String = "Недавние",
    showIcons: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 20.dp),
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Medium
        )
        if (passwordsList.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                passwordsList.forEach {
                    SmallPasswordsListRow(item = it, showIcon = showIcons)
                }
            }
        }
    }
}

@Composable
fun SmallPasswordsListRow(item: PasswordRecordEntity, showIcon: Boolean = false) {
    val clipData = ClipData.newPlainText("Copied password:", item.passwordRecordPassword)
        .apply {
            description.extras = PersistableBundle().apply {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }
    var copy = false
    val clipboardManager = LocalClipboard.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.password_icon_string_description),
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        Text(
            text = item.passwordRecordName,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
                copy = !copy
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_button_copy),
                contentDescription = "copy"
            )
        }
        LaunchedEffect(copy) {
            clipboardManager.setClipEntry(ClipEntry(clipData))
        }
    }
}