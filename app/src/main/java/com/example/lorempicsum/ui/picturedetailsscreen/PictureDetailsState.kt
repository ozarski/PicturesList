package com.example.lorempicsum.ui.picturedetailsscreen

import com.example.lorempicsum.domain.model.PictureDetailsModel

data class PictureDetailsState (
    val picture: PictureDetailsModel? = null,
    val error: Exception? = null,
    val isLoading: Boolean = false,
    val id: Long? = null
)