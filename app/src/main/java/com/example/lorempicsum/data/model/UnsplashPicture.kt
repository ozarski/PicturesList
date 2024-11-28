package com.example.lorempicsum.data.model

import com.example.lorempicsum.domain.model.PictureListItem

data class UnsplashPicture(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

fun UnsplashPicture.toPictureListItem(): PictureListItem {
    return PictureListItem(
        id = id,
        authorName = author,
        width = width,
        height = height,
        downloadUrl = download_url
    )
}
