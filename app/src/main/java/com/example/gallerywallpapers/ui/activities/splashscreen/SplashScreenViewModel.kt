package com.example.gallerywallpapers.ui.activities.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLASH_DELAY: Long = 2000

@HiltViewModel
class SplashScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        executeDelay()
    }

    private fun executeDelay() {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _isLoading.value = false
        }
    }
}
