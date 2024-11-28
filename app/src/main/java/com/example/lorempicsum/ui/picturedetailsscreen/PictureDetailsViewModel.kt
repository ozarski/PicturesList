package com.example.lorempicsum.ui.picturedetailsscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lorempicsum.data.model.toPictureDetailsModel
import com.example.lorempicsum.domain.repository.PictureRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PictureDetailsViewModelFactory::class)
class PictureDetailsViewModel @AssistedInject constructor(
    private val pictureRepository: PictureRepository,
    @Assisted private val id: Long
) : ViewModel() {

    private val _state = mutableStateOf(PictureDetailsState())
    val state: State<PictureDetailsState> = _state

    private fun getPictureDetails() {
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

@AssistedFactory
interface PictureDetailsViewModelFactory {
    fun create(id: Long): PictureDetailsViewModel
}