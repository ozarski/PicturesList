package com.example.lorempicsum.domain.repository

import com.example.lorempicsum.data.datasource.DEFAULT_PAGE_SIZE
import com.example.lorempicsum.data.model.UnsplashPicture

interface PictureRepository {

    suspend fun getPictures(page: Int, limit: Int = DEFAULT_PAGE_SIZE): List<UnsplashPicture>
    suspend fun getPictureDetails(id: Long): UnsplashPicture
}