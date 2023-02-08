package com.example.gallerywallpapers.domain.interfaces

import com.example.gallerywallpapers.domain.models.WallpaperModel

interface GalleryRepository {

    suspend fun getWallpapers(category: String): List<WallpaperModel>

    suspend fun getWallpaperById(id: Int): WallpaperModel?

    suspend fun getCategories(): List<String>
}
