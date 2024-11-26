package com.example.lorempicsum.ui.picturelistscreen

import androidx.lifecycle.ViewModel
import com.example.lorempicsum.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val pictureRepository: PictureRepository
): ViewModel()
