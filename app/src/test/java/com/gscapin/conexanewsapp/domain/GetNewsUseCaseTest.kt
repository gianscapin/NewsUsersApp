package com.gscapin.conexanewsapp.domain

import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.domain.useCases.GetNewsUseCase
import com.gscapin.conexanewsapp.repository.NewRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNewsUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: NewRepository

    lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

        getNewsUseCase = GetNewsUseCase(repository)
    }

    @Test
    fun `when api return something then get values from api`() = runBlocking {
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
        coEvery { repository.getNews() } returns newsList

        // WHEN
        val response = getNewsUseCase()

        // THEN
        // Verificar que se llamó a la api
        coVerify(exactly = 1) {  repository.getNews() }

        // comprobar el primer elemento
        assert(newsList.first() == response.first())

    }
}