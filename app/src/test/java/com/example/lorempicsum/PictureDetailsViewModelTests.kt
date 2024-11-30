package com.example.lorempicsum

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.example.lorempicsum.data.model.UnsplashPicture
import com.example.lorempicsum.domain.repository.PictureRepository
import com.example.lorempicsum.ui.base.PICTURE_ID_NAV_PARAM
import com.example.lorempicsum.ui.picturedetailsscreen.PictureDetailsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PictureDetailsViewModelTests {

    @Mock
    private lateinit var pictureRepository: PictureRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test init load`() = runTest {
        val savedStateHandle = SavedStateHandle().apply {
            set(PICTURE_ID_NAV_PARAM, "1")
        }


        val mock = mockDetails()
        whenever(pictureRepository.getPictureDetails(1)).thenReturn(mock)

        val pictureDetailsViewModel =
            PictureDetailsViewModel(pictureRepository, savedStateHandle, Application())

        assertNotNull(pictureDetailsViewModel.state.value.picture)
        assertEquals(mock.download_url, pictureDetailsViewModel.state.value.picture!!.downloadUrl)
        assertEquals(mock.author, pictureDetailsViewModel.state.value.picture!!.author)
        assertEquals(mock.width, pictureDetailsViewModel.state.value.picture!!.width)
        assertEquals(mock.height, pictureDetailsViewModel.state.value.picture!!.height)
        assertEquals(mock.id, pictureDetailsViewModel.state.value.picture!!.id)

    }

    @Test
    fun `test reload`() = runTest {
        val savedStateHandle = SavedStateHandle().apply {
            set(PICTURE_ID_NAV_PARAM, "1")
        }


        val mock = mockDetails()
        whenever(pictureRepository.getPictureDetails(1)).thenReturn(mock)

        val pictureDetailsViewModel =
            PictureDetailsViewModel(pictureRepository, savedStateHandle, Application())

        assertNotNull(pictureDetailsViewModel.state.value.picture)
        assertEquals(mock.download_url, pictureDetailsViewModel.state.value.picture!!.downloadUrl)
        assertEquals(mock.author, pictureDetailsViewModel.state.value.picture!!.author)
        assertEquals(mock.width, pictureDetailsViewModel.state.value.picture!!.width)
        assertEquals(mock.height, pictureDetailsViewModel.state.value.picture!!.height)
        assertEquals(mock.id, pictureDetailsViewModel.state.value.picture!!.id)

        pictureDetailsViewModel.reload()

        assertNotNull(pictureDetailsViewModel.state.value.picture)
        assertEquals(mock.download_url, pictureDetailsViewModel.state.value.picture!!.downloadUrl)
        assertEquals(mock.author, pictureDetailsViewModel.state.value.picture!!.author)
        assertEquals(mock.width, pictureDetailsViewModel.state.value.picture!!.width)
        assertEquals(mock.height, pictureDetailsViewModel.state.value.picture!!.height)
        assertEquals(mock.id, pictureDetailsViewModel.state.value.picture!!.id)
        assert(!pictureDetailsViewModel.state.value.isLoading)
    }

    @Test
    fun `test init load error`() = runTest {
        val savedStateHandle = SavedStateHandle().apply {
            set(PICTURE_ID_NAV_PARAM, "1")
        }


        val mock = mockDetails()
        whenever(pictureRepository.getPictureDetails(1)).thenThrow(MockitoException("error message"))

        val pictureDetailsViewModel =
            PictureDetailsViewModel(pictureRepository, savedStateHandle, Application())

        assertEquals("error message", pictureDetailsViewModel.state.value.error)
        assertNull(pictureDetailsViewModel.state.value.picture)
    }


    @Test
    fun `test  reload error`() = runTest {
        val savedStateHandle = SavedStateHandle().apply {
            set(PICTURE_ID_NAV_PARAM, "1")
        }


        val mock = mockDetails()

        whenever(pictureRepository.getPictureDetails(1)).thenReturn(mock)

        val pictureDetailsViewModel =
            PictureDetailsViewModel(pictureRepository, savedStateHandle, Application())

        assertNotNull(pictureDetailsViewModel.state.value.picture)
        assertEquals(mock.download_url, pictureDetailsViewModel.state.value.picture!!.downloadUrl)
        assertEquals(mock.author, pictureDetailsViewModel.state.value.picture!!.author)
        assertEquals(mock.width, pictureDetailsViewModel.state.value.picture!!.width)
        assertEquals(mock.height, pictureDetailsViewModel.state.value.picture!!.height)
        assertEquals(mock.id, pictureDetailsViewModel.state.value.picture!!.id)


        whenever(pictureRepository.getPictureDetails(1)).thenThrow(MockitoException("error message"))

        pictureDetailsViewModel.reload()

        assertEquals("error message", pictureDetailsViewModel.state.value.error)
        assertNull(pictureDetailsViewModel.state.value.picture)
    }

    private fun mockDetails(): UnsplashPicture {
        return UnsplashPicture(
            id = 1,
            download_url = "https://picture.download.url",
            author = "picture author",
            width = 100,
            height = 100,
            url = "https://picture.url"
        )
    }
}