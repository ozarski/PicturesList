package com.example.lorempicsum.ui.picturelistscreen

import android.content.res.Resources
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lorempicsum.R
import com.example.lorempicsum.data.model.toPictureListItem
import com.example.lorempicsum.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val pictureRepository: PictureRepository
) : ViewModel(){

    private val _state = mutableStateOf(PictureListState())
    val state: State<PictureListState> = _state

    init{
        getPictures()
    }

    private fun getPictures(){
        viewModelScope.launch {
            try{
                _state.value = _state.value.copy(isLoading = true)
                val pictures = pictureRepository.getPictures(_state.value.page)
                    .map { it.toPictureListItem() }
                _state.value = _state.value.copy(
                    isLoading = false,
                    pictures = pictures
                )
            } catch (e: Exception){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: Resources.getSystem().getString(R.string.error_message)
                )
            }
        }
    }

    fun loadMore(){
        _state.value = _state.value.copy(
            page = _state.value.page + 1,
            loadingMore = true
        )
        viewModelScope.launch {
            try{
                val newPictures = pictureRepository.getPictures(_state.value.page)
                    .map { it.toPictureListItem() }
                _state.value = _state.value.copy(
                    loadingMore = false,
                    pictures = _state.value.pictures + newPictures
                )
            } catch (e: Exception){
                _state.value = _state.value.copy(
                    loadingMore = false,
                    error = e.message ?: Resources.getSystem().getString(R.string.error_message),
                    page = _state.value.page - 1
                )
            }
        }
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            page = 1
        )
        viewModelScope.launch {
            try{
                val pictures = pictureRepository.getPictures(_state.value.page)
                    .map { it.toPictureListItem() }
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    pictures = pictures,
                    error = ""
                )
            } catch (e: Exception){
                delay(100)
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    error = e.message ?: Resources.getSystem().getString(R.string.error_message),
                    pictures = emptyList()
                )
            }
        }
    }

    fun reload(){
        _state.value = _state.value.copy(
            error = ""
        )
        getPictures()
    }
}