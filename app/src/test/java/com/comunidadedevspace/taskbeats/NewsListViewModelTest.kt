package com.comunidadedevspace.taskbeats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.presentation.NewsListVIewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val newsService: NewsService = mock()

    private lateinit var underTest: NewsListVIewModel

    @Test
    fun `GIVEN request succeed news WHEN fetch THEN return list`() { runBlocking {

        val expected = listOf<NewsDto>(
            NewsDto(
                id = "id1",
                content = "content1",
                imageUrl = "image1",
                title = "title1"
            )
        )
        val response = NewsResponse(category = "tech", data = expected)

        whenever(newsService.fetchNews()).thenReturn(response)
        underTest = NewsListVIewModel(
            newsService
        )

        val result = underTest.newsListLiveData.getOrAwaitValue()
        assert(result == expected)

    } }
}