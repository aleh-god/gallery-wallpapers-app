package com.example.gallerywallpapers.di

import com.example.gallerywallpapers.data.datasources.CategoryLocalDataSource
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
    abstract fun stringsListImpl_to_CategoryLocalDataSource(
        stringsListImpl: CategoryLocalDataSource.StringsListImpl
    ): CategoryLocalDataSource
}
