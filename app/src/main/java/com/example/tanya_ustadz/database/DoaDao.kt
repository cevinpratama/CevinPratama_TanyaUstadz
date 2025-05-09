package com.example.tanya_ustadz.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tanya_ustadz.model.Doa
import kotlinx.coroutines.flow.Flow

@Dao
interface DoaDao {

    @Insert
    suspend fun insert(doa: Doa)

    @Update
    suspend fun update(doa: Doa)

    @Query("SELECT * FROM doa ORDER BY tanggal DESC")
    fun getDoa(): Flow<List<Doa>>

    @Query("SELECT * FROM doa WHERE id = :id")
    suspend fun getDoaById(id : Long):Doa?

    @Query("UPDATE doa SET isFavorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, favorite: Boolean)


    @Query("DELETE FROM doa WHERE id = :id")
    suspend fun deleteById(id : Long)
}