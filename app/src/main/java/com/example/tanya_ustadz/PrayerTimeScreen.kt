package com.example.tanya_ustadz

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.tanya_ustadz.api.PrayerTimesResponse
import com.example.tanya_ustadz.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimeScreen() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val currentPrayer = "Maghrib"
    val colorText = if (isDark) Color.White else Color.Black
    val context = LocalContext.current
    val defaultCity = stringResource(R.string.kota)
    val kota by remember { mutableStateOf(defaultCity) }
    var prayerTimes by remember { mutableStateOf<PrayerTimesResponse?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val fetchPrayerTimes: (String) -> Unit = { cityName ->
        isLoading = true
        val call = RetrofitClient.apiService.getPrayerTimes(cityName)
        call.enqueue(object : Callback<PrayerTimesResponse> {
            override fun onResponse(call: Call<PrayerTimesResponse>, response: Response<PrayerTimesResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    prayerTimes = response.body()
                    errorMessage = ""
                } else {
                    errorMessage = context.getString(R.string.error)
                }
            }

            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                isLoading = false
                errorMessage = t.message ?: context.getString(R.string.error)
            }
        })
    }

    LaunchedEffect(kota) {
        fetchPrayerTimes(kota)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                    Text(
                        text = stringResource(R.string.jadwal),
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


        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF000000))
            }
        } else {

            prayerTimes?.items?.firstOrNull()?.let { prayerItem ->
                val inputFormatter =
                    DateTimeFormatter.ofPattern("yyyy-M-d")
                val outputFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val formattedDate = try {
                    LocalDate.parse(prayerItem.date_for, inputFormatter)
                        .format(outputFormatter)
                } catch (e: Exception) {
                    prayerItem.date_for
                }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = kota,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formattedDate,
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
                    PrayerRow(stringResource(id = R.string.subuh), prayerItem.fajr, currentPrayer)
                    PrayerRow(stringResource(id = R.string.dzuhur), prayerItem.dhuhr, currentPrayer)
                    PrayerRow(stringResource(id = R.string.ashar), prayerItem.asr, currentPrayer)
                    PrayerRow(stringResource(id = R.string.maghrib), prayerItem.maghrib, currentPrayer)
                    PrayerRow(stringResource(id = R.string.isya), prayerItem.isha, currentPrayer)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { share(context) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (isDark) Color.White else Color(0xFF1976D2)
                )
            ) {
                IconButton(onClick = { }) {
                    androidx.compose.material3.Icon(
                        Icons.Default.Share,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource( id = R.string.bagikanJadwal))
            }

            Spacer(modifier = Modifier.height(16.dp))

           }
          }
        }
    }}

fun share(context: Context) {
    val textToShare = """
        Jadwal Sholat - 12-12-2025
        
        Subuh: 05.00
        Dzuhur: 12.00
        Ashar: 16.00
        Maghrib: 17.00
        Isya: 19.00
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Jadwal Sholat")
        putExtra(Intent.EXTRA_TEXT, textToShare)
    }

    val chooser = Intent.createChooser(intent, "Bagikan jadwal via...")
    context.startActivity(chooser)
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
fun PrayerRow(name: String, time: String, currentPrayer: String, center: Boolean = false) {
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
