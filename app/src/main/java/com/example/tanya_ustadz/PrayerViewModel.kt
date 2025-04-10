package com.example.tanya_ustadz

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tanya_ustadz.api.PrayerTimesResponse
import com.example.tanya_ustadz.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrayerViewModel : ViewModel() {
    var kota = mutableStateOf("Jakarta")
    var prayerTimes = mutableStateOf<PrayerTimesResponse?>(null)
    var recentSearches = mutableStateOf(listOf<String>())
    var isLoading = mutableStateOf(false)

    fun fetchPrayerTimes(context: Context, city: String) {
        isLoading.value = true
        val call = RetrofitClient.apiService.getPrayerTimes(city)
        call.enqueue(object : Callback<PrayerTimesResponse> {
            override fun onResponse(
                call: Call<PrayerTimesResponse>,
                response: Response<PrayerTimesResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    prayerTimes.value = response.body()
                    kota.value = city
                    if (!recentSearches.value.contains(city)) {
                        recentSearches.value = (listOf(city) + recentSearches.value).take(5)
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.errorData), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                isLoading.value = false
                Toast.makeText(context, t.message ?: context.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
