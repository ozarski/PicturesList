package com.example.lorempicsum.ui.picturedetailsscreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lorempicsum.data.model.toPictureDetailsModel
import com.example.lorempicsum.domain.repository.PictureRepository
import com.example.lorempicsum.ui.base.PICTURE_ID_NAV_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureDetailsViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    savedStateHandle: SavedStateHandle,
    private val appContext: Application
) : ViewModel() {

    private val _state = mutableStateOf(PictureDetailsState())
    val state: State<PictureDetailsState> = _state

    init {
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

    fun copyUrlToClipboard() {
        _state.value.picture?.let { picture ->
            val clipboardManager =
                appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = android.content.ClipData.newPlainText("Image URL", picture.downloadUrl)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(appContext, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }
}