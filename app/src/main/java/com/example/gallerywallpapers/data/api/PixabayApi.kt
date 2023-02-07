package com.example.gallerywallpapers.data.api

import com.example.gallerywallpapers.BuildConfig
import com.example.gallerywallpapers.data.models.PixabayModel
import retrofit2.http.GET
import retrofit2.http.Query

/*
The search term q needs to be URL encoded: "{ KEY }" has to replaced with your API key.
https://pixabay.com/api/?key=???&category=fashion
https://pixabay.com/api/?key=???&id=7760037
 */

interface PixabayApi {

    @GET("api/")
    suspend fun getPixabayModelsByCategory(
        @Query("key") key: String = BuildConfig.PIXABAY_API_KEY,
        @Query("category") category: String
    ): PixabayModel

    @GET("api/")
    suspend fun getPixabayModelById(
        @Query("key") key: String = BuildConfig.PIXABAY_API_KEY,
        @Query("id") id: Int
    ): PixabayModel
}
