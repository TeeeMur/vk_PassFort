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
import androidx.navigation.compose.rememberNavController
import com.example.passfort.R
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.navigation.Screen

@Immutable
data class NavigationBarItem(
    @StringRes val nameOpenActivity: Int,
    @DrawableRes val icon: Int,
)


@Composable
fun NavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavigationBarItem(
            nameOpenActivity = R.string.home_screen,
            icon = R.drawable.navbar_home,
        ),
        NavigationBarItem(
            nameOpenActivity = R.string.password_list,
            icon = R.drawable.navbar_passwords,
        ),
        NavigationBarItem(
            nameOpenActivity = R.string.password_generator,
            icon = R.drawable.navbar_key,
        ),
        NavigationBarItem(
            nameOpenActivity = R.string.settings,
            icon = R.drawable.navbar_settings,
        )
    )

    val selectedItemByIndex = remember {
        mutableStateOf(0)
    }

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
                    NavItem(ImageVector.vectorResource(item.icon),
                        stringResource(item.nameOpenActivity),
                        index == selectedItemByIndex.value,
                        navController
                    ) { selectedItemByIndex.value = index }

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
                .clickable { navController.navigate(Screen.AddPassword.route){
                    popUpTo(Screen.PasswordList.route) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
                    selectedItemByIndex.value = 4}
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
            isSelected: Boolean,
            navController: NavHostController,
            onClick: () -> Unit) {

    val iconColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable {
                navController.navigate(navigateString) {
                popUpTo(Screen.PasswordList.route) { inclusive = false }
                launchSingleTop = true
                restoreState = true
            };
                onClick()
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
fun PrevieNavBar(){
    var navController = rememberNavController()
    PassFortTheme { NavigationBar(navController) }
}
