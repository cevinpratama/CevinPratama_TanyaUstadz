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
import com.example.tanya_ustadz.ui.theme.AppColors.backgroundDarkMediumContrast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CariScreen() {

    val backgroundColor = if (isSystemInDarkTheme()) {
        backgroundDarkMediumContrast
    } else {
        Color.White
    }
    val colorScreen = if (isSystemInDarkTheme()) {
        Color.Black
    } else {
        Color.White
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(4.dp)
                    .height(80.dp)
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
                                text = stringResource(id = R.string.cari),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Italic,

                            )
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColor
                    ),
                    actions = {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { }) {
                                androidx.compose.material3.Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
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
                .background(colorScreen)
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

