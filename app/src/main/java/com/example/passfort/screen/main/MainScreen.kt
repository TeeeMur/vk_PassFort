package com.example.passfort.screen.main

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.PersistableBundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
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
import com.example.passfort.designSystem.components.PreviewNavBar
import com.example.passfort.designSystem.components.RecentsList
import com.example.passfort.designSystem.components.SearchBar
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.navigation.Screen
import com.example.passfort.viewModel.MainScreenViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.drop
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
    onClickPassword: (Long) -> Unit,
    navigationBar: @Composable () -> Unit = {
        PreviewNavBar()
    },
) {
    val anchors = DraggableAnchors<Anchors> {
        Anchors.Start at 0F
        Anchors.End at -800F
    }
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = Anchors.Start,
            anchors = anchors,
        )
    }
    val recentsLazyColumnState = rememberLazyListState()
    val lazyListScrollEnabled by remember{derivedStateOf{
        anchoredDraggableState.currentValue == Anchors.End
    }}
    val pagerState = rememberPagerState { mainScreenImages.size }
    val screenHeightInDp = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp()
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(end = 16.dp),
                title = {
                    val name = if (viewModel.userName.collectAsState().value != "") ", ${viewModel.userName.collectAsState().value}!" else "!"
                    Text(
                        text = "Привет$name",
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                },
                actions = {
                    IconButton(
                        onClick = {navController.navigate(Screen.Settings.route)}
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = stringResource(R.string.profile_icon_string_description),
                            tint = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = navigationBar,
    ) { scaffoldPaddingValues ->
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
                        orientation = Orientation.Vertical,
                    )
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(
                        screenHeightInDp
                                - scaffoldPaddingValues.calculateTopPadding()
                                - WindowInsets.statusBars.getTop(LocalDensity.current).dp
                                + scaffoldPaddingValues.calculateBottomPadding()
                                + 16.dp
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
                val recents = viewModel.recentPasswords.collectAsState()
                val pinned = viewModel.pinnedPasswords.collectAsState()
                if (recents.value.isNotEmpty() or pinned.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(bottom = scaffoldPaddingValues.calculateBottomPadding() / 2f)
                    ) {
                        if (pinned.value.isNotEmpty()) {
                            BackgroundList(
                                modifier = Modifier.padding(vertical = 6.dp),
                                title = stringResource(R.string.main_screen_pinned_title),
                                viewModel = viewModel,
                                showIcons = true,
                                onClickPassword = onClickPassword
                            )
                        }
                        if (recents.value.isNotEmpty()) {
                            RecentsList(
                                viewModel = viewModel,
                                titleModifier = Modifier.padding(top = 8.dp, start = 6.dp),
                                scrollEnabled = lazyListScrollEnabled,
                                listState = recentsLazyColumnState,
                                onClickPassword = onClickPassword,
                                onPin = { viewModel.pinPassword(it) },
                                onDelete = { viewModel.deletePassword(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BackgroundList(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel,
    title: String = "Недавние",
    showIcons: Boolean = false,
    onClickPassword: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.outline)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 14.dp),
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Medium
        )
        val pinned = viewModel.pinnedPasswords.collectAsState()
        Column(
            modifier = Modifier.padding(top = 4.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            pinned.value.forEach {
                SmallPasswordsListRow(
                    item = it,
                    showIcon = showIcons,
                    onClickPassword = onClickPassword
                )
            }
        }
    }
}

@Composable
fun SmallPasswordsListRow(
    item: PasswordRecordEntity,
    showIcon: Boolean = false,
    onClickPassword: (Long) -> Unit
) {
    val clipData = ClipData.newPlainText("Copied password:", item.recordPassword)
        .apply {
            description.extras = PersistableBundle().apply {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }
    var copy by remember{mutableStateOf(false)}
    val clipboardManager = LocalClipboard.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = {onClickPassword(item.id)}),
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
            text = item.recordName,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            modifier = Modifier.size(32.dp),
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
            snapshotFlow { copy }.drop(1).collect {
                clipboardManager.setClipEntry(ClipEntry(clipData))
            }
        }
    }
}