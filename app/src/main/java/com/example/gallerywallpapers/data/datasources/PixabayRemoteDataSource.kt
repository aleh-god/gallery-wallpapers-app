package com.example.gallerywallpapers.data.datasources

import com.example.gallerywallpapers.data.api.PixabayApi
import com.example.gallerywallpapers.data.models.Hit
import com.example.gallerywallpapers.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PixabayRemoteDataSource @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val pixabayApi: PixabayApi
) {

    suspend fun getWallpapersByCategory(category: String): List<Hit> = withContext(ioDispatcher) {
        pixabayApi.getPixabayModelsByCategory(category = category).hits
    }

    suspend fun getWallpaperById(id: Int): Hit? = withContext(ioDispatcher) {
        pixabayApi.getPixabayModelById(id = id).hits.firstOrNull()
    }
}
