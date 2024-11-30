package com.example.lorempicsum.ui.picturelistscreen

import android.app.Application
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
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val appContext: Application
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
            } catch (e: HttpException){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = appContext.getString(R.string.http_error_message)
                )
            }
            catch (e: SocketTimeoutException){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = appContext.getString(R.string.no_connection_error_message)
                )
            }
            catch (e: UnknownHostException){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = appContext.getString(R.string.no_connection_error_message)
                )
            }
            catch (e: Exception){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = appContext.getString(R.string.base_error_message)
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
            } catch (e: HttpException){
                _state.value = _state.value.copy(
                    loadingMore = false,
                    error = appContext.getString(R.string.http_error_message),
                    page = _state.value.page - 1
                )
            } catch (e: SocketTimeoutException){
                _state.value = _state.value.copy(
                    loadingMore = false,
                    error = appContext.getString(R.string.no_connection_error_message),
                    page = _state.value.page - 1
                )
            } catch (e: UnknownHostException){
                _state.value = _state.value.copy(
                    loadingMore = false,
                    error = appContext.getString(R.string.no_connection_error_message),
                    page = _state.value.page - 1
                )
            } catch (e: Exception){
                _state.value = _state.value.copy(
                    loadingMore = false,
                    error = appContext.getString(R.string.base_error_message),
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
            } catch (e: HttpException){
                delay(100)
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    error = appContext.getString(R.string.http_error_message),
                    pictures = emptyList()
                )
            } catch (e: SocketTimeoutException){
                delay(100)
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    error = appContext.getString(R.string.no_connection_error_message),
                    pictures = emptyList()
                )
            } catch (e: UnknownHostException){
                delay(100)
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    error = appContext.getString(R.string.no_connection_error_message),
                    pictures = emptyList()
                )
            } catch (e: Exception){
                delay(100)
                _state.value = _state.value.copy(
                    isRefreshing = false,
                    error = appContext.getString(R.string.base_error_message),
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