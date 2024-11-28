package com.example.lorempicsum.ui.picturedetailsscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lorempicsum.data.model.toPictureDetailsModel
import com.example.lorempicsum.domain.repository.PictureRepository
import com.example.lorempicsum.ui.base.PICTURE_ID_NAV_PARAM
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureDetailsViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(PictureDetailsState())
    val state: State<PictureDetailsState> = _state

    init{
        savedStateHandle.get<String>(PICTURE_ID_NAV_PARAM)?.let { id ->
            getPictureDetails(id.toLong())
        }
    }

    private fun getPictureDetails(id: Long) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val picture = pictureRepository.getPictureDetails(id).toPictureDetailsModel()
                _state.value = _state.value.copy(picture = picture, isLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value.copy(
                        error = e.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
            }
        }
    }
}