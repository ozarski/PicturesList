package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PictureListScreen(
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    if(state.isLoading){
        CircularProgressIndicator()
    }
    else if(state.error.isNotBlank()){
        Text(text = state.error)
    }
    else{
        Text(text = state.pictures.size.toString())
    }
}