package com.example.gallerywallpapers.di

import com.example.gallerywallpapers.androidutils.WallpaperManagerBehaviour
import com.example.gallerywallpapers.data.datasources.CategoryDataSource
import com.example.gallerywallpapers.data.repositories.GalleryRepositoryImpl
import com.example.gallerywallpapers.domain.interfaces.GalleryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("FunctionName")
abstract class AppBindModule {

    @Binds
    abstract fun bindGalleryRepositoryImpl_to_GalleryRepository(
        repositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository

    @Binds
    abstract fun localImpl_to_CategoryDataSource(
        localImpl: CategoryDataSource.LocalImpl
    ): CategoryDataSource

    @Binds
    abstract fun androidImpl_to_WallpaperManagerBehaviour(
        localImpl: WallpaperManagerBehaviour.AndroidImpl
    ): WallpaperManagerBehaviour
}
