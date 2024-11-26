package com.example.lorempicsum.di

import com.example.lorempicsum.data.repository.PictureRepositoryImpl
import com.example.lorempicsum.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PictureRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPictureRepository(pictureRepositoryImpl: PictureRepositoryImpl): PictureRepository
}