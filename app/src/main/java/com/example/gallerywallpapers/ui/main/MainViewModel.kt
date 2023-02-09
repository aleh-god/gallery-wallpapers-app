package com.example.gallerywallpapers.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.domain.interfaces.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<Int>()
    val uiEvent: Flow<Int> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        loadDataList()
    }

    private fun loadDataList() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isFetchingData = true) }
            try {
                val res = galleryRepository.getCategories()
                _uiState.update { it.copy(
                    isFetchingData = false,
                    hasErrorState = false,
                    dataList = res
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isFetchingData = false,
                    hasErrorState = true
                ) }
                _uiEvent.send(R.string.message_error_data_load)
            }
        }
    }

    fun reloadDataList() {
        loadDataList()
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<String> = emptyList(),
        val hasErrorState: Boolean = false
    )
}
