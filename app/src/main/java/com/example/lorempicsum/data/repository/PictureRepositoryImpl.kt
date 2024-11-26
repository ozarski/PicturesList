package com.example.lorempicsum.data.repository

import com.example.lorempicsum.data.datasource.UnsplashApi
import com.example.lorempicsum.data.model.UnsplashPicture
import com.example.lorempicsum.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val unsplashApi: UnsplashApi
): PictureRepository {
    override suspend fun getPictures(page: Int, limit: Int): List<UnsplashPicture> {
        return unsplashApi.getPictures(page, limit)
    }
}