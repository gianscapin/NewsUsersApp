package com.gscapin.conexanewsapp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.navigation.NewsScreens
import com.gscapin.conexanewsapp.presentation.home.NewsUiState
import com.gscapin.conexanewsapp.screens.home.HomeScreen
import com.gscapin.conexanewsapp.screens.newDetails.NewDetailsScreen
import com.gscapin.conexanewsapp.utils.NEWS_DETAIL_SCREEN_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_EMPTY_LIST_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_ERROR_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_LOADING_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_SUCCESS_TEST_TAG
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun homeScreenLoadingTest() {
        val loadingUiState = NewsUiState.Loading

        composeTestRule.setContent {
            HomeScreen(onNewClick = {}, uiState = loadingUiState)
        }

        composeTestRule.onNodeWithTag(NEWS_SCREEN_LOADING_TEST_TAG).assertExists()

    }

    @Test
    fun homeScreenErrorTest() {
        val textError = "Error de carga"

        val errorUiState = NewsUiState.Error(message = textError)

        composeTestRule.setContent {
            HomeScreen(onNewClick = {}, uiState = errorUiState)
        }

        composeTestRule.onNodeWithTag(NEWS_SCREEN_ERROR_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(NEWS_SCREEN_ERROR_TEST_TAG)
            .assertTextEquals("Error: $textError")
    }

    @Test
    fun homeScreenSuccessEmptyListTest() {
        val emptyUiState = NewsUiState.Success(news = emptyList())

        composeTestRule.setContent {
            HomeScreen(onNewClick = {}, uiState = emptyUiState)
        }

        composeTestRule.onNodeWithTag(NEWS_SCREEN_EMPTY_LIST_TEST_TAG).assertExists()
    }

    @Test
    fun homeScreenSuccessTest() {

        val successUiState = NewsUiState.Success(news = newsList)

        composeTestRule.setContent {
            HomeScreen(onNewClick = {}, uiState = successUiState)
        }

        composeTestRule.onNodeWithTag(NEWS_SCREEN_SUCCESS_TEST_TAG).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun homeScreenSuccessClickItemTest() {

        val successUiState = NewsUiState.Success(news = newsList)

        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = NewsScreens.HomeScreen.name) {
                composable(NewsScreens.HomeScreen.name) {
                    HomeScreen(
                        onNewClick = {
                            navController.navigate(NewsScreens.NewDetailsScreen.name + "/$id")
                        },
                        uiState = successUiState
                    )
                }

                val detailName = NewsScreens.NewDetailsScreen.name
                composable(
                    route = "$detailName/{newsId}",
                    arguments = listOf(navArgument("newsId") { type = NavType.IntType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt("newsId")?.let {
                        NewDetailsScreen(newsItem = successUiState.news.first()) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }

        composeTestRule.onNodeWithTag(NEWS_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG.plus("_1")).performClick()
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(NEWS_DETAIL_SCREEN_TEST_TAG))
        composeTestRule.onNodeWithTag(NEWS_DETAIL_SCREEN_TEST_TAG).assertExists()
    }
}