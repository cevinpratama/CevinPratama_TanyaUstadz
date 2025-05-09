package com.example.tanya_ustadz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doa")
data class Doa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama_doa: String,
    val isi: String,
    val tanggal: String,
    val isFavorite: Boolean = false,
    val isDeleted: Boolean = false
)
