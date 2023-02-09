package com.example.gallerywallpapers.ui.wallpaper

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.androidutils.WallpaperManagerBehaviour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private var wallpaperManagerBehaviour: WallpaperManagerBehaviour
) : ViewModel() {

    private val _uiState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    private val _uiEvent = Channel<Int>()
    val uiEvent: Flow<Int> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    fun setWallpaper(resource: Bitmap) {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            _uiState.update { true }
            val result = wallpaperManagerBehaviour.setWallpaper(resource)
            _uiEvent.send(
                if (result) R.string.message_wall_installed
                else R.string.message_wall_install_error
            )
            _uiState.update { false }
        }
    }
}
