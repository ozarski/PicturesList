package com.example.lorempicsum.ui.picturelistscreen

import com.example.lorempicsum.domain.model.PictureListItem

data class PictureListState(
    val isLoading: Boolean = false,
    val pictures: List<PictureListItem> = emptyList(),
    val error: Exception? = null,
    val page: Int = 1,
    val loadingMore: Boolean = false,
    val isRefreshing: Boolean = false
)
