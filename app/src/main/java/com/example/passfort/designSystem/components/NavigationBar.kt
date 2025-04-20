package com.example.passfort.designSystem.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.navigation.Screen
import kotlin.enums.EnumEntries

enum class ENavigationItem(
    val nameResId: Int,
    val iconResId: Int,
    val route: String
) {
    HOME(
        nameResId = R.string.home_screen,
        iconResId = R.drawable.navbar_home,
        route = Screen.HomeScreen.route
    ),
    PASSWORDS(
        nameResId = R.string.password_list,
        iconResId = R.drawable.navbar_passwords,
        route = Screen.PasswordList.route
    ),
    GENERATOR(
        nameResId = R.string.password_generator,
        iconResId = R.drawable.navbar_key,
        route = Screen.PasswordGenerator.route

    ),
    SETTINGS(
        nameResId = R.string.settings,
        iconResId = R.drawable.navbar_settings,
        route = Screen.Settings.route

    );
}

@Composable
fun NavigationBar(navController: NavHostController) {
    val navItems = ENavigationItem.entries.toList()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 10.dp)
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(5.dp, shape = CircleShape)
            ) {
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(40.dp))
                    .height(70.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    NavItem(
                        ImageVector.vectorResource(item.iconResId),
                        stringResource(item.nameResId),
                        item.route,
                        navController
                    )

                    if (index == 1) {
                        Box(
                            modifier = Modifier
                                .offset { IntOffset(x = 0, y = -50) }
                                .size(100.dp)
                                .padding(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            ) {}
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = -100) }
                .align(Alignment.TopCenter)
                .clickable(onClick = onAddPassword)
                .size(90.dp)
                .padding(5.dp)
                .shadow(10.dp, shape = CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(35.dp)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_button_add),
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun NavItem(iconImage: ImageVector,
            navigateString: String,
            route: String,
            navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val iconColor = if (currentRoute == route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable {
                navController.navigate(navigateString) {
                    popUpTo(Screen.PasswordList.route) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = iconImage,
            tint = iconColor,
            contentDescription = null,
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewNavBar(){
    var navController = rememberNavController()
    PassFortTheme { NavigationBar(navController, {}) }
}
