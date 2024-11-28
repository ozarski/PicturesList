package com.example.lorempicsum.ui.base

sealed class Screen(val route: String) {
    data object PictureListScreen : Screen("picture_list_screen")
    data object PictureDetailScreen : Screen("picture_detail_screen")
}