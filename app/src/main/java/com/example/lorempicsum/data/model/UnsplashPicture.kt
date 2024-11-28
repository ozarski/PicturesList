package com.example.lorempicsum.data.model

import com.example.lorempicsum.domain.model.PictureDetailsModel
import com.example.lorempicsum.domain.model.PictureListItem

data class UnsplashPicture(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: Long,
    val url: String,
    val width: Int
)

fun UnsplashPicture.toPictureListItem(): PictureListItem {
    return PictureListItem(
        id = id,
        downloadUrl = download_url
    )
}

fun UnsplashPicture.toPictureDetailsModel(): PictureDetailsModel {
    return PictureDetailsModel(
        id = id,
        author = author,
        downloadUrl = download_url,
        width = width,
        height = height
    )
}
