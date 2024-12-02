package com.example.lorempicsum

import com.example.lorempicsum.data.datasource.DEFAULT_PAGE_SIZE
import com.example.lorempicsum.data.datasource.UnsplashApi
import com.example.lorempicsum.data.model.UnsplashPicture
import com.example.lorempicsum.data.repository.PictureRepositoryImpl
import com.example.lorempicsum.domain.repository.PictureRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class PictureRepositoryTests {

    @Mock
    lateinit var api: UnsplashApi

    private lateinit var repository: PictureRepository

    @Before
    fun setUp() {
        repository = PictureRepositoryImpl(api)
    }

    @Test
    fun `test getPictures`() = runTest {
        val mockPictures = mockData()
        whenever(api.getPictures(1, DEFAULT_PAGE_SIZE)).thenReturn(mockPictures)

        val pictures = repository.getPictures(1)

        assertEquals(mockPictures, pictures)
    }

    @Test
    fun `test getPictureDetails`() = runTest {
        val mockPicture = UnsplashPicture(
            id = 1,
            author = "picture author",
            url = "https://picture.url",
            download_url = "https://picture.download.url",
            width = 100,
            height = 100
        )

        whenever(api.getPictureDetails(1)).thenReturn(mockPicture)

        val picture = repository.getPictureDetails(1)

        assertEquals(mockPicture, picture)
    }

    @Test(expected = SocketTimeoutException::class)
    fun `test getPictures error`() = runTest {
        given(api.getPictures(1, DEFAULT_PAGE_SIZE)).willAnswer {
            throw SocketTimeoutException()
        }

        repository.getPictures(1, DEFAULT_PAGE_SIZE)
    }

    @Test(expected = SocketTimeoutException::class)
    fun `test getPictureDetails error`() = runTest {
        given(api.getPictureDetails(1)).willAnswer{
            throw SocketTimeoutException()
        }

        repository.getPictureDetails(1)
    }

    private fun mockData(page: Int = 0): List<UnsplashPicture> {
        return List(20) {
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