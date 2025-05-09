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

    @Query("SELECT * FROM doa WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getDoa(): Flow<List<Doa>> // Menampilkan doa yang tidak dihapus

    @Query("SELECT * FROM doa WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getDeletedDoa(): Flow<List<Doa>> // Menampilkan doa yang sudah dihapus (Recycle Bin)

    @Query("SELECT * FROM doa WHERE id = :id")
    suspend fun getDoaById(id: Long): Doa?

    @Query("UPDATE doa SET isFavorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, favorite: Boolean)

    @Query("UPDATE doa SET isDeleted = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long) // Soft delete

    @Query("UPDATE doa SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreById(id: Long) // Restore (Undo)
}
