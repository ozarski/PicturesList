package com.example.lorempicsum.domain.model

data class PictureListItem(
    val id: String,
    val authorName: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String
)