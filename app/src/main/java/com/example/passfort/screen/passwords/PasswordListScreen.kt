package com.example.passfort.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passfort.viewModel.PasswordViewModel
import com.example.passfort.viewModel.PasswordListState
import com.example.passfort.model.PasswordItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.passfort.designSystem.components.PasswordCard
import com.example.passfort.designSystem.theme.PassFortTheme

@Composable
fun PasswordListScreen(
    viewModel: PasswordViewModel = hiltViewModel(),
    navController: NavHostController,
    onAddPassword: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            com.example.passfort.designSystem.components.NavigationBar(
                navController,
                onAddPassword
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
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
            Spacer(modifier = Modifier.height(12.dp))

            when (uiState) {
                is PasswordListState.Loading -> LoadingScreen()
                is PasswordListState.Error -> ErrorScreen(uiState.message, onRetry = { viewModel.retry() })
                is PasswordListState.Empty -> EmptyScreen()
                is PasswordListState.Success -> {
                    PasswordSections(
                        pinnedPasswords = uiState.pinnedPasswords,
                        allPasswords = uiState.allPasswords,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Search, contentDescription = "Поиск", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            if (query.isEmpty()) {
                Text(
                    text = "Search password",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PasswordSections(
    pinnedPasswords: List<PasswordItem>,
    allPasswords: List<PasswordItem>,
    navController: NavHostController
) {
    var pinnedExpanded by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                        imageVector = if (pinnedExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight,
                        contentDescription = if (pinnedExpanded) "Свернуть" else "Развернуть"
                    )
                }
            }
            if (pinnedExpanded) {
                items(pinnedPasswords.size) { index ->
                    PasswordCard(
                        pinnedPasswords[index],
                        navController,
                        ""
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
                navController,
                ""
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
fun ErrorScreen(message: String, onRetry: () -> Unit) {
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
            Button(onClick = onRetry) {
                Text("Повторить")
            }
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
    PassFortTheme { PasswordListScreen(viewModel,navController, {}) }
}
