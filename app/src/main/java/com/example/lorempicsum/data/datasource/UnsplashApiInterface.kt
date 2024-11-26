package com.example.lorempicsum.data.datasource

import com.example.lorempicsum.data.model.UnsplashPicture
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiInterface {

    @GET("/v2/list")
    suspend fun getPictures(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<UnsplashPicture>
}