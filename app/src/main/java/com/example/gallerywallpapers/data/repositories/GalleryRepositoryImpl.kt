package com.example.gallerywallpapers.data.repositories

import com.example.gallerywallpapers.data.datasources.CategoryDataSource
import com.example.gallerywallpapers.data.datasources.PixabayRemoteDataSource
import com.example.gallerywallpapers.data.mappers.toWallpaperModel
import com.example.gallerywallpapers.di.DefaultDispatcher
import com.example.gallerywallpapers.domain.interfaces.GalleryRepository
import com.example.gallerywallpapers.domain.models.WallpaperModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val pixabayRemoteDataSource: PixabayRemoteDataSource,
    private val categoryDataSource: CategoryDataSource
) : GalleryRepository {

    override suspend fun getWallpapers(category: String): List<WallpaperModel> =
        withContext(defaultDispatcher) {
            pixabayRemoteDataSource.getWallpapersByCategory(category)
                .map { it.toWallpaperModel() }
    }

    override suspend fun getWallpaperById(id: Int): WallpaperModel? =
        withContext(defaultDispatcher) {
            pixabayRemoteDataSource.getWallpaperById(id)?.toWallpaperModel()
    }

    override suspend fun getCategories(): List<String> = categoryDataSource.getCategories()
}
