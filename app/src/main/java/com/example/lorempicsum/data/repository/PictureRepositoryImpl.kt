package com.example.lorempicsum.data.repository

import com.example.lorempicsum.data.datasource.UnsplashApiInterface
import com.example.lorempicsum.data.model.UnsplashPicture
import com.example.lorempicsum.domain.repository.PictureRepository

class PictureRepositoryImpl(
    private val unsplashApiInterface: UnsplashApiInterface
): PictureRepository {
    override suspend fun getPictures(page: Int, limit: Int): List<UnsplashPicture> {
        return unsplashApiInterface.getPictures(page, limit)
    }
}