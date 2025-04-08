package com.example.tanya_ustadz

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahScreen() {

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(4.dp)
                    .height(70.dp)
            )
            {
                TopAppBar(
                    modifier = Modifier.height(80.dp),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = stringResource(id = R.string.tambah),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Italic,
                            )
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = cardColor
                    ),
                    actions = {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { }) {
                                androidx.compose.material3.Icon(
                                    Icons.Default.Search,
                                    contentDescription = stringResource(R.string.cari),

                                )
                            }
                        }
                    }
                )
            }
        }
    )
    { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
                .background(backgroundColor)
        )
        {
            item {
                Column ( modifier = Modifier
                    .fillMaxSize()
                )
                {

                    Text("hello word")

                }
            }
        }
    }
}

