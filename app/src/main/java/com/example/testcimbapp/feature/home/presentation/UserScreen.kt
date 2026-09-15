package com.example.testcimbapp.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcimbapp.feature.home.domain.model.Address
import com.example.testcimbapp.feature.home.domain.model.Company
import com.example.testcimbapp.feature.home.domain.model.Geo
import com.example.testcimbapp.feature.home.domain.model.User
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    when {
        uiState.isLoading -> {
            UserLoadingScreen()
        }

        uiState.error != null -> {
            UserErrorScreen(
                message = uiState.error ?: "Terjadi kesalahan",
                onRetry = {
                    viewModel.getUsers()
                }
            )
        }

        else -> {
            UserContent(
                uiState = uiState
            )
        }
    }
}

@Composable
fun UserContent(
    uiState: UserUiState
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredUsers = uiState.users.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.email.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {
        UserHeader()

        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            }
        )

        UserList(
            users = filteredUsers,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun UserHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1555A8),
                        Color(0xFF326DC4)
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp
                )
        ) {
            Text(
                text = "Kontak",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Berikut ini merupakan daftar list kontak. Yang menampilkan Nama dan Email.",
                color = Color.White.copy(alpha = 0.95f),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
            .height(42.dp),
        shape = RoundedCornerShape(28.dp),
        color = Color(0xFFEFF3F9)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF7890B8),
                modifier = Modifier.size(24.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF19386C)
                ),
                decorationBox = { innerTextField ->

                    if (query.isEmpty()) {
                        Text(
                            text = "Cari nama atau email...",
                            color = Color(0xFF8195BA),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun UserList(
    users: List<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            bottom = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            UserItem(user)
        }
    }
}

@Composable
private fun UserItem(
    user: User
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar
            Box(
                modifier = Modifier.size(54.dp)
            ) {
                UserAvatar(
                    user = user,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0C2D60)
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = user.email,
                    fontSize = 11.sp,
                    color = Color(0xFF7890B8),
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF8297BA),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun UserAvatar(
    user: User,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (user.id % 2 == 0) {
        Color(0xFFE2DEFF)
    } else {
        Color(0xFFD9E9FF)
    }

    val iconColor = if (user.id % 2 == 0) {
        Color(0xFF706A9C)
    } else {
        Color(0xFF55759F)
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = user.name,
            tint = iconColor,
            modifier = Modifier
                .fillMaxSize(0.65f)
        )
    }
}

@Composable
fun UserLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(42.dp),
                color = Color(0xFF1555A8),
                strokeWidth = 4.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Memuat data...",
                fontSize = 14.sp,
                color = Color(0xFF7890B8)
            )
        }
    }
}

@Composable
fun UserErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = Color(0xFFE05252),
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Terjadi Kesalahan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0C2D60)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                fontSize = 14.sp,
                color = Color(0xFF7890B8),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Coba Lagi"
                )
            }
        }
    }
}

private val sampleUsers = listOf(

    User(
        id = 1,
        name = "Andi Setiawan",
        username = "andisetiawan",
        email = "andi.setiawan@conspiracyhotelgroup.com",
        phone = "08123456789",
        website = "conspiracyhotelgroup.com",
        address = Address(
            street = "Jl. Sudirman",
            suite = "Suite 1",
            city = "Jakarta",
            zipcode = "10220",
            geo = Geo(
                lat = "-6.2088",
                lng = "106.8456"
            )
        ),
        company = Company(
            name = "Conspiracy Hotel Group",
            catchPhrase = "Every Single Second is Precious",
            bs = "Hotel Management"
        )
    ),

    User(
        id = 2,
        name = "Bella Dewi",
        username = "belladewi",
        email = "bella.dewi@conspiracyhotelgroup.com",
        phone = "08123456789",
        website = "conspiracyhotelgroup.com",
        address = Address(
            street = "Jl. Asia Afrika",
            suite = "Suite 2",
            city = "Bandung",
            zipcode = "40112",
            geo = Geo(
                lat = "-6.9214",
                lng = "107.6098"
            )
        ),
        company = Company(
            name = "Conspiracy Hotel Group",
            catchPhrase = "Every Single Second is Precious",
            bs = "Hotel Management"
        )
    ),

    User(
        id = 3,
        name = "Bella Dewi",
        username = "belladewi",
        email = "bella.dewi@conspiracyhotelgroup.com",
        phone = "08123456789",
        website = "conspiracyhotelgroup.com",
        address = Address(
            street = "Jl. Asia Afrika",
            suite = "Suite 2",
            city = "Bandung",
            zipcode = "40112",
            geo = Geo(
                lat = "-6.9214",
                lng = "107.6098"
            )
        ),
        company = Company(
            name = "Conspiracy Hotel Group",
            catchPhrase = "Every Single Second is Precious",
            bs = "Hotel Management"
        )
    )
)

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    MaterialTheme {
        UserContent(
            uiState = UserUiState(
                users = sampleUsers
            )
        )
    }
}