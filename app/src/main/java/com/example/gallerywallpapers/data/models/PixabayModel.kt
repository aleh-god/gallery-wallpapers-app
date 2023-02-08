package com.example.gallerywallpapers.data.models

data class PixabayModel(
    val hits: List<Hit> = emptyList(),
    val total: Int = 0,
    val totalHits: Int = 0
)
