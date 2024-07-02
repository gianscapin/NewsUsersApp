package com.gscapin.conexanewsapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.domain.useCases.GetNewsUseCase
import com.gscapin.conexanewsapp.presentation.home.HomeViewModel
import com.gscapin.conexanewsapp.presentation.home.NewsUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {


    @RelaxedMockK
    private lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var homeViewModel: HomeViewModel

//    private val standardCoroutineDispatcher = StandardTestDispatcher()
//    private val testCoroutineScope = TestScope(standardCoroutineDispatcher)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

        //homeViewModel = HomeViewModel(getNewsUseCase)

        // probarlo sin init
        homeViewModel = HomeViewModel(getNewsUseCase = getNewsUseCase)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created, get all news and set the first value`() = runTest {
        val newsList = listOf(
            New(
                id = 1,
                slug = "lorem-ipsum",
                url = "https://jsonplaceholder.org/posts/lorem-ipsum",
                title = "Lorem ipsum dolor sit amet",
                content = "Lorem ipsum content...",
                image = "https://dummyimage.com/800x430/FFFFFF/lorem-ipsum.png&text=jsonplaceholder.org",
                thumbnail = "https://dummyimage.com/200x200/FFFFFF/lorem-ipsum.png&text=jsonplaceholder.org",
                status = "published",
                category = "lorem",
                publishedAt = "04/02/2023 13:25:21",
                updatedAt = "14/03/2023 17:22:20",
                userId = 1
            ),
            New(
                id = 2,
                slug = "second-news",
                url = "https://example.com/news/2",
                title = "Second News Article",
                content = "Content of the second news article...",
                image = "https://example.com/images/news2.jpg",
                thumbnail = "https://example.com/images/news2_thumb.jpg",
                status = "published",
                category = "technology",
                publishedAt = "01/03/2023 09:15:00",
                updatedAt = "02/03/2023 11:30:45",
                userId = 2
            ),
            New(
                id = 3,
                slug = "breaking-news",
                url = "https://news.com/breaking",
                title = "Breaking News!",
                content = "Important breaking news content...",
                image = "https://news.com/images/breaking.png",
                thumbnail = "https://news.com/images/breaking_thumb.png",
                status = "published",
                category = "world",
                publishedAt = "10/03/2023 18:00:00",
                updatedAt = "10/03/2023 18:30:15",
                userId = 1
            )
        )

        coEvery { getNewsUseCase() } returns newsList

        // WHEN
        homeViewModel.getNews()

        // THEN
        val firstValue = (homeViewModel.uiState.value as? NewsUiState.Success)?.news?.first()
        assert(firstValue == newsList.first())
    }
}