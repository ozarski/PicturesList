package com.example.lorempicsum.ui.picturelistscreen

import com.example.lorempicsum.domain.model.PictureListItem

data class PictureListState(
    val isLoading: Boolean = false,
    val pictures: List<PictureListItem> = emptyList(),
    val error: String = "",
    val page: Int = 1
)
