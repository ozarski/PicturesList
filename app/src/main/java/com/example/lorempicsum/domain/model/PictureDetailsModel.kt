package com.example.lorempicsum.domain.model

data class PictureDetailsModel (
    val id: Long,
    val author: String,
    val downloadUrl: String,
    val width: Int,
    val height: Int
)