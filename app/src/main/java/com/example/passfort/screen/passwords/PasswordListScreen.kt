package com.example.passfort.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.passfort.R
import com.example.passfort.designSystem.components.PasswordCard
import com.example.passfort.designSystem.components.SearchBar
import com.example.passfort.designSystem.theme.PassFortTheme
import com.example.passfort.model.PasswordItem
import com.example.passfort.viewModel.PasswordListState
import com.example.passfort.viewModel.PasswordViewModel
import com.valentinilk.shimmer.shimmer

@Composable
fun PasswordListScreen(
    viewModel: PasswordViewModel = hiltViewModel(),
    navController: NavHostController,
    onAddPassword: () -> Unit,
    onClickPassword: (Long) -> Unit,
    onItemClick: (Long) -> Unit,
    onItemDelete: (PasswordItem) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            com.example.passfort.designSystem.components.NavigationBar(
                navController,
                onAddPassword,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding(),
                    bottom = 40.dp)
                .padding(20.dp)
        ) {
            var searchValue by remember {mutableStateOf("")}
            SearchBar(
                value = searchValue,
                placeholder = stringResource(id = R.string.search_passwords_field_placeholder),
            ) {
                searchValue = it
            }
            Spacer(modifier = Modifier.height(12.dp))

            when (uiState) {
                is PasswordListState.Loading -> LoadingScreen()
                is PasswordListState.Error -> ErrorScreen(uiState.message)
                is PasswordListState.Empty -> EmptyScreen()
                is PasswordListState.Success -> {
                    PasswordSections(
                        pinnedPasswords = uiState.pinnedPasswords,
                        allPasswords = uiState.allPasswords,
                        onClickPassword = onClickPassword,
                        onDeletePassword = { passwordItem ->
                            //
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordSections(
    pinnedPasswords: List<PasswordItem>,
    allPasswords: List<PasswordItem>,
    onClickPassword: (Long) -> Unit,
    onDeletePassword: (PasswordItem) -> Unit

) {
    val scrollState = rememberLazyListState()
    var pinnedExpanded by remember { mutableStateOf(true) }

    LazyColumn(modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        if (pinnedPasswords.isNotEmpty()) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { pinnedExpanded = !pinnedExpanded }
                ) {
                    Text(
                        text = "Закрепленные",
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 22.sp,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (pinnedExpanded) Icons.Filled.KeyboardArrowDown
                        else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = if (pinnedExpanded) "Свернуть" else "Развернуть"
                    )
                }
            }
            if (pinnedExpanded) {
                items(pinnedPasswords.size) { index ->
                    PasswordCard(
                        pinnedPasswords[index],
                        onClickPassword = onClickPassword,
                             
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Text(
                text = "Все пароли",
                color = MaterialTheme.colorScheme.inverseSurface,
                fontSize = 22.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(allPasswords.size) { index ->
            PasswordCard(allPasswords[index],
                onClickPassword = onClickPassword
            )
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun LoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(5) {  // например, 5 placeholder-элементов
            ShimmerPasswordCard()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ShimmerPasswordCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shimmer(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Нет сохранённых паролей",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewListScreen(){
    var viewModel = hiltViewModel<PasswordViewModel>()
    var navController = rememberNavController()
    PassFortTheme { PasswordListScreen(viewModel,navController, {},{}, {}, {}) }
}
