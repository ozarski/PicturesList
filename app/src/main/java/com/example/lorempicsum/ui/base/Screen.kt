package com.example.lorempicsum.ui.base

sealed class Screen(val route: String) {
    data object PictureListScreen : Screen(PICTURE_LIST_SCREEN_ROUTE)
    data object PictureDetailScreen : Screen(PICTURE_DETAILS_SCREEN_ROUTE)
}