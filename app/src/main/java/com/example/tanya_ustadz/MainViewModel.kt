package com.example.tanya_ustadz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanya_ustadz.database.DoaDao
import com.example.tanya_ustadz.model.Doa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.combine

class MainViewModel(private val dao: DoaDao) : ViewModel() {
    private val _showFavoritesOnly = mutableStateOf(false)
    val showFavoritesOnly: State<Boolean> = _showFavoritesOnly
    val filteredData: StateFlow<List<Doa>> = combine(
        dao.getDoa(),
        snapshotFlow { _showFavoritesOnly.value }
    ) { doas, showFavorites ->
        if (showFavorites) doas.filter { it.isFavorite } else doas
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun toggleFavoritesFilter() {
        _showFavoritesOnly.value = !_showFavoritesOnly.value
    }

    val data: StateFlow<List<Doa>> = dao.getDoa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun updateFavorite(id: Long, favorite: Boolean) {
        viewModelScope.launch {
            dao.updateFavorite(id, favorite)
        }
    }
}







