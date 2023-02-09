package com.example.gallerywallpapers.data.datasources

import javax.inject.Inject

interface CategoryDataSource {

    suspend fun getCategories(): List<String>

    class LocalImpl @Inject constructor() : CategoryDataSource {

        private val categories = listOf<String>(
            "backgrounds",
            "fashion",
            "nature",
            "science",
            "education",
            "feelings",
            "health",
            "people",
            "religion",
            "places",
            "animals",
            "industry",
            "computer",
            "food",
            "sports",
            "transportation",
            "travel",
            "buildings",
            "business",
            "music"
        )

        override suspend fun getCategories(): List<String> = categories
    }
}
