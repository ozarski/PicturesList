package com.example.lorempicsum

import com.example.lorempicsum.data.model.UnsplashPicture
import com.example.lorempicsum.data.model.toPictureListItem
import com.example.lorempicsum.domain.repository.PictureRepository
import com.example.lorempicsum.ui.picturelistscreen.PictureListViewModel
import junit.framework.TestCase.assertEquals
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
class PictureListViewModelTests {

    @Mock
    private lateinit var pictureRepository: PictureRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test getPictures() default` () = runTest {
        val mockData = mockData()

        whenever(pictureRepository.getPictures(1)).thenReturn(mockData)

        val freshPictureListViewModel = PictureListViewModel(pictureRepository)
        assertEquals(freshPictureListViewModel.state.value.pictures, mockData.map { it.toPictureListItem() })

    }

    @Test
    fun `test loadMore() page 2`() = runTest{

        whenever(pictureRepository.getPictures(1)).thenReturn(mockData(0))
        whenever(pictureRepository.getPictures(2)).thenReturn(mockData(1))

        val pictureListViewModel = PictureListViewModel(pictureRepository)

        pictureListViewModel.loadMore()
        val combinedMockData = mockData(0) + mockData(1)
        assertEquals(pictureListViewModel.state.value.pictures, combinedMockData.map { it.toPictureListItem() })
    }

    @Test
    fun `test refresh() and reload()`() = runTest{
        val mockData = mockData()
        whenever(pictureRepository.getPictures(1)).thenReturn(mockData)

        val pictureListViewModel = PictureListViewModel(pictureRepository)
        pictureListViewModel.refresh()

        assertEquals(pictureListViewModel.state.value.pictures, mockData.map { it.toPictureListItem() })
        assert(!pictureListViewModel.state.value.isRefreshing)

        pictureListViewModel.reload()

        assertEquals(pictureListViewModel.state.value.pictures, mockData.map { it.toPictureListItem() })
        assert(!pictureListViewModel.state.value.isLoading)
    }

    @Test
    fun `test init with error`() = runTest{
        whenever(pictureRepository.getPictures(1)).thenThrow(MockitoException("error"))

        val pictureListViewModel = PictureListViewModel(pictureRepository)

        assertEquals(pictureListViewModel.state.value.error, "error")
    }

    @Test
    fun `test loadMore with error`() = runTest{
        whenever(pictureRepository.getPictures(1)).thenReturn(mockData())
        whenever(pictureRepository.getPictures(2)).thenThrow(MockitoException("error"))

        val pictureListViewModel = PictureListViewModel(pictureRepository)

        pictureListViewModel.loadMore()

        assertEquals(pictureListViewModel.state.value.error, "error")
    }

    @Test
    fun `test refresh with error`() = runTest{
        whenever(pictureRepository.getPictures(1)).thenThrow(MockitoException("error"))

        val pictureListViewModel = PictureListViewModel(pictureRepository)

        pictureListViewModel.refresh()

        assertEquals(pictureListViewModel.state.value.error, "error")
    }

    @Test
    fun `test reload with error`() = runTest{
        whenever(pictureRepository.getPictures(1)).thenThrow(MockitoException("error"))

        val pictureListViewModel = PictureListViewModel(pictureRepository)

        pictureListViewModel.reload()

        assertEquals(pictureListViewModel.state.value.error, "error")
    }


    private fun mockData(page: Int = 0): List<UnsplashPicture>{
        return List(20){
            UnsplashPicture(
                id = it.toLong() + page * 20,
                download_url = "downloadUrl/${it + page * 20}",
                author = "author ${it + page * 20}",
                width = 1920,
                height = 1080,
                url = "url/${it + page * 20}"
            )
        }
    }
}