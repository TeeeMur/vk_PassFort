package com.example.passfort.ui

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
import com.example.passfort.viewmodel.PasswordViewModel
import com.example.passfort.viewmodel.PasswordListState
import com.example.passfort.model.PasswordItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun PasswordListScreen(viewModel: PasswordViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
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
                        allPasswords = uiState.allPasswords
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
                    style = MaterialTheme.typography.bodyMedium
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
    allPasswords: List<PasswordItem>
) {
    var pinnedExpanded by remember { mutableStateOf(true) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                        style = MaterialTheme.typography.titleMedium
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
                    PasswordCard(pinnedPasswords[index])
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Text(
                text = "Все пароли",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(allPasswords.size) { index ->
            PasswordCard(allPasswords[index])
        }
    }
}



@Composable
fun PasswordCard(item: PasswordItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),  // Светло-серый цвет
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(1.dp)  // Легкая тень
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontFamily = FontFamily.Default,  // Используем Roboto по умолчанию
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = item.username,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF757575)  // DarkGrey_100
                )
                // Индикатор срока действия
                when {
                    item.daysToExpire <= 0 -> {
                        Text(
                            text = "Истёк",
                            fontSize = 14.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item.daysToExpire in 1..7 -> {
                        Text(
                            text = "Скоро истекает",
                            fontSize = 14.sp,
                            color = Color(0xFFFFA000), // оранжево-желтый оттенок
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            // Если пароль скомпрометирован, показываем иконку и текст "скомпрометирован"
            if (item.isCompromised) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Пароль скомпрометирован",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "СКОМПРОМЕТИРОВАН",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(onClick = { /* Логика копирования */ }) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Копировать",
                    tint = Color.Gray
                )
            }
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

//ТУТ БУДЕТ НАВБАР
@Composable
fun BottomNavigationBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = Color.White,
            tonalElevation = 2.dp,
            shadowElevation = 4.dp,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Главная",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.List,
                        contentDescription = "Пароли",
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(32.dp)) // место для центральной кнопки
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.VpnKey,
                        contentDescription = "Ключи",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Настройки",
                        tint = Color.Gray
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {},
            containerColor = Color(0xFF3949AB), // насыщенный темно-синий цвет
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
            elevation = FloatingActionButtonDefaults.elevation(2.dp) // минимальная тень
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Добавить", tint = Color.White, modifier = Modifier.size(28.dp))
        }
    }
}
