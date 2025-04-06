package com.example.tanya_ustadz

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun PrayerTimeScreen() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val currentPrayer = "Maghrib"
    val colorText = if (isDark) Color.White else Color.Black


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                    Text(
                        text = stringResource(id = R.string.jadwal),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        fontStyle = FontStyle.Italic
                    )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        IconButton(onClick = { }) {
                            androidx.compose.material3.Icon(
                                Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor
                ),
                modifier = Modifier
                    .height(70.dp)
                    .shadow(4.dp)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.jadwal),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "12-12-2025",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    PrayerRow("Subuh", "05.00", currentPrayer)
                    PrayerRow("Dzuhur", "12.00", currentPrayer)
                    PrayerRow("Ashar", "16.00", currentPrayer)
                    PrayerRow("Maghrib", "17.00", currentPrayer)
                    PrayerRow("Isya", "19.00", currentPrayer)
                }
            }


        }
    }
}


//
//@Composable
//fun PrayerTimeColumn(name: String, time: String, center: Boolean = false) {
//    Column(
//        horizontalAlignment = if (center) Alignment.CenterHorizontally else Alignment.Start
//    ) {
//        Text(text = time, fontSize = 25.sp, fontStyle = FontStyle.Italic)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
//    }
//}
@Composable
fun PrayerRow(name: String, time: String, currentPrayer: String) {
    val isDark = isSystemInDarkTheme()
    val colorText = if (isDark) Color.White else Color.Black
    val isActive = name == currentPrayer
    val textColor = if (isActive) Color(0xFF319AFF) else colorText

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }) {
                androidx.compose.material3.Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    tint = textColor
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            )
        }

        Text(
            text = time,
            fontSize = 18.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}
