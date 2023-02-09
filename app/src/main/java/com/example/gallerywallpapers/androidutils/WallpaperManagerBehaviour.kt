package com.example.gallerywallpapers.androidutils

import android.graphics.Bitmap
import com.example.gallerywallpapers.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface WallpaperManagerBehaviour {

    suspend fun setWallpaper(resource: Bitmap): Boolean

    class AndroidImpl @Inject constructor(
        @DefaultDispatcher
        private val defaultDispatcher: CoroutineDispatcher,
        private val wallpaperManager: android.app.WallpaperManager
    ) : WallpaperManagerBehaviour {

        override suspend fun setWallpaper(resource: Bitmap): Boolean =
            withContext(defaultDispatcher) {
                try {
                    wallpaperManager.setBitmap(resource)
                    true
                } catch (e: Exception) {
                    false
                }
            }
    }
}
