package com.example.lorempicsum.di

import com.example.lorempicsum.data.datasource.UNSPLASH_BASE_URL
import com.example.lorempicsum.data.datasource.UnsplashApi
import com.example.lorempicsum.data.repository.PictureRepositoryImpl
import com.example.lorempicsum.domain.repository.PictureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUnsplashApi(): UnsplashApi {
        return Retrofit.Builder()
            .baseUrl(UNSPLASH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApi::class.java)
    }
}