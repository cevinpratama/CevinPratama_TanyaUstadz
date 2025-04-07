package com.example.tanya_ustadz.api

data class PrayerTimesResponse(
    val query: String,
    val prayer_method_name: String,
    val timezone: String,
    val qibla_direction: String,
    val latitude: String,
    val longitude: String,
    val country: String,
    val items: List<PrayerItem>
)

data class PrayerItem(
    val date_for: String,
    val fajr: String,
    val shurooq: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)
