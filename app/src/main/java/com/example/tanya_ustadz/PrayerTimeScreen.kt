package com.example.tanya_ustadz

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.tanya_ustadz.api.PrayerItem
import com.example.tanya_ustadz.api.PrayerTimesResponse
import com.example.tanya_ustadz.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimeScreen() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val colorText = if (isDark) Color.White else Color.Black
    val context = LocalContext.current

    val defaultCity = stringResource(R.string.kota)
    var kota by remember { mutableStateOf(defaultCity) }
    var searchText by remember { mutableStateOf("") }
    var recentSearches by remember { mutableStateOf(listOf<String>()) }
    var prayerTimes by remember { mutableStateOf<PrayerTimesResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var currentPrayer by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val availableCities = listOf("Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Medan")

    fun fetchPrayerTimes(city: String) {
        isLoading = true
        val call = RetrofitClient.apiService.getPrayerTimes(city)
        call.enqueue(object : Callback<PrayerTimesResponse> {
            override fun onResponse(call: Call<PrayerTimesResponse>, response: Response<PrayerTimesResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    prayerTimes = response.body()
                    if (!recentSearches.contains(city)) {
                        recentSearches = (listOf(city) + recentSearches).take(5)
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                isLoading = false
                Toast.makeText(context, t.message ?: context.getString(R.string.error), Toast.LENGTH_SHORT).show()
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.jadwal),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = cardColor),
                modifier = Modifier.shadow(4.dp).height(70.dp)
            )
        },
        containerColor = backgroundColor

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState())
        )
        {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(stringResource(R.string.cariKota)) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (searchText.isNotBlank()) {
                                kota = searchText.trim()
                                fetchPrayerTimes(kota)
                                searchText = ""
                                keyboardController?.hide()

                            }
                        },
                        enabled = searchText.isNotBlank()
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Cari")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotBlank()) {
                            kota = searchText.trim()
                            fetchPrayerTimes(kota)
                            searchText = ""
                            keyboardController?.hide()
                        }
                    }
                )
            )


            if (searchText.isNotBlank()) {
                val suggestions = availableCities.filter {
                    it.contains(searchText.trim(), ignoreCase = true)
                }

                suggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                kota = suggestion
                                searchText = ""
                                fetchPrayerTimes(suggestion)
                            }
                            .padding(vertical = 8.dp)
                    )
                }
            } else if (recentSearches.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.pencarianTerakhir),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(recentSearches) { city ->
                        AssistChip(
                            onClick = {
                                kota = city
                                fetchPrayerTimes(city)
                            },
                            label = { Text(city) }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                prayerTimes?.items?.firstOrNull()?.let { prayerItem ->
                    val formattedDate = try {
                        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d")
                        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        LocalDate.parse(prayerItem.date_for, inputFormatter).format(outputFormatter)
                    } catch (e: Exception) {
                        prayerItem.date_for
                    }
                    currentPrayer = getCurrentPrayer(prayerItem)

                    Text(
                        text = "Kota ${kota.replaceFirstChar { it.uppercase() }}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = formattedDate,
                        fontSize = 20.sp,
                        color = colorText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            PrayerRow(stringResource(id = R.string.subuh), prayerItem.fajr, currentPrayer)
                            PrayerRow(stringResource(id = R.string.dzuhur), prayerItem.dhuhr, currentPrayer)
                            PrayerRow(stringResource(id = R.string.ashar), prayerItem.asr, currentPrayer)
                            PrayerRow(stringResource(id = R.string.maghrib), prayerItem.maghrib, currentPrayer)
                            PrayerRow(stringResource(id = R.string.isya), prayerItem.isha, currentPrayer)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { share(context, formattedDate, prayerItem, kota) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.bagikanJadwal))
                    }
                }
            }
        }
    }

}




fun share(context: Context, date: String, prayerItem: PrayerItem, kota: String) {
    val jadwal = context.getString(R.string.jadwal)
    val textToShare = """
        $jadwal - $date
        Lokasi: $kota
        
        Subuh: ${prayerItem.fajr}
        Dzuhur: ${prayerItem.dhuhr}
        Ashar: ${prayerItem.asr}
        Maghrib: ${prayerItem.maghrib}
        Isya: ${prayerItem.isha}
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
                Icon(
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

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentPrayer(prayerItem: PrayerItem): String {
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    val now = LocalTime.now()

    return try {
        val fajr = LocalTime.parse(prayerItem.fajr.lowercase(), timeFormatter)
        val dhuhr = LocalTime.parse(prayerItem.dhuhr.lowercase(), timeFormatter)
        val asr = LocalTime.parse(prayerItem.asr.lowercase(), timeFormatter)
        val maghrib = LocalTime.parse(prayerItem.maghrib.lowercase(), timeFormatter)
        val isha = LocalTime.parse(prayerItem.isha.lowercase(), timeFormatter)

        when {
            now.isBefore(fajr) -> "Subuh"
            now.isBefore(dhuhr) -> "Dzuhur"
            now.isBefore(asr) -> "Ashar"
            now.isBefore(maghrib) -> "Maghrib"
            now.isBefore(isha) -> "Isya"
            else -> "Subuh"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Subuh"
    }
}
