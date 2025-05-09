package com.example.tanya_ustadz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanya_ustadz.database.DoaDao
import com.example.tanya_ustadz.model.Doa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetailViewModel (private val dao: DoaDao): ViewModel(){
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US)

    fun insert(nama_doa : String, isi : String){
        val doa = Doa(
            tanggal = formatter.format(Date()),
            nama_doa = nama_doa,
            isi = isi
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(doa)
        }
    }
    suspend fun getDoa(id : Long): Doa? {
        return dao.getDoaById(id)
    }

    fun update(id: Long, nama_doa: String,isi: String){
        val doa = Doa(
            id = id,
            tanggal = formatter.format(Date()),
            nama_doa = nama_doa,
            isi = isi
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.update(doa)
        }
    }

    fun delete(id: Long, onUndoAvailable: (Doa) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val deletedDoa = dao.getDoaById(id)
            if (deletedDoa != null) {
                dao.softDeleteById(id)
                onUndoAvailable(deletedDoa)
            }
        }
    }
    fun restore(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreById(id)
        }
    }


}