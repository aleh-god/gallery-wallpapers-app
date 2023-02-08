package com.example.gallerywallpapers.data.mappers

import com.example.gallerywallpapers.data.models.Hit
import com.example.gallerywallpapers.domain.models.WallpaperModel

fun Hit.toWallpaperModel(): WallpaperModel = WallpaperModel(
    id = this.id,
    largeImageURL = this.largeImageURL,
    previewURL = this.previewURL
)
