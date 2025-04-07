package com.example.tanya_ustadz.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerApiService {
    @GET("{city}.json?key=api_key")
    fun getPrayerTimes(@Path("city") city: String): Call<PrayerTimesResponse>
}