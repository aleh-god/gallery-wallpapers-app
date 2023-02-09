package com.example.gallerywallpapers.ui.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.domain.interfaces.GalleryRepository
import com.example.gallerywallpapers.domain.models.WallpaperModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CATEGORY_NAVIGATION_ARGUMENT = "category"

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val category = state.get<String>(CATEGORY_NAVIGATION_ARGUMENT)

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
            category?.let { category ->
                _uiState.update { it.copy(isFetchingData = true) }
                try {
                    _uiState.update { it.copy(
                        isFetchingData = false,
                        hasErrorState = false,
                        dataList = galleryRepository.getWallpapers(category)
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
    }

    fun reloadDataList() {
        loadDataList()
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<WallpaperModel> = emptyList(),
        val hasErrorState: Boolean = false
    )
}
