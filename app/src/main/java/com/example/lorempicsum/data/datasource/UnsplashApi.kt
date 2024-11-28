package com.example.lorempicsum.data.datasource

import com.example.lorempicsum.data.model.UnsplashPicture
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET(PICTURE_LIST_ENDPOINT)
    suspend fun getPictures(
        @Query(PAGE_QUERY_PARAM) page: Int,
        @Query(LIMIT_QUERY_PARAM) limit: Int
    ): List<UnsplashPicture>

    @GET(PICTURE_DETAILS_ENDPOINS)
    suspend fun getPictureDetails(
        @Path(ID_PATH_PARAM) id: Long
    ): UnsplashPicture
}