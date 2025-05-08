package com.example.tanya_ustadz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanya_ustadz.database.DoaDao
import com.example.tanya_ustadz.model.Doa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: DoaDao) : ViewModel() {

    val data: StateFlow<List<Doa>> = dao.getDoa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}