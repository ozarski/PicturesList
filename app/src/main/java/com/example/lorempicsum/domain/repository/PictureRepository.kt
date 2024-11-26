package com.example.lorempicsum.domain.repository

import com.example.lorempicsum.data.model.UnsplashPicture

interface PictureRepository {

    suspend fun getPictures(page: Int, limit: Int): List<UnsplashPicture>
}