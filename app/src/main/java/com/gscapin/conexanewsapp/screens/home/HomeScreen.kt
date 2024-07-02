package com.gscapin.conexanewsapp.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gscapin.conexanewsapp.R
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.presentation.home.NewsUiState
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_EMPTY_LIST_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_ERROR_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_LOADING_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG
import com.gscapin.conexanewsapp.utils.NEWS_SCREEN_SUCCESS_TEST_TAG

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNewClick: (Int) -> Unit,
    uiState: NewsUiState
) {

    when (uiState) {
        is NewsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag(NEWS_SCREEN_LOADING_TEST_TAG),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is NewsUiState.Success -> {

            if (uiState.news.isNotEmpty()) {
                var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

                val filteredNews = uiState.news.filter {
                    it.title.contains(
                        searchQuery.text,
                        ignoreCase = true
                    ) || it.content.contains(searchQuery.text, ignoreCase = true)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag(NEWS_SCREEN_SUCCESS_TEST_TAG)
                ) {
                    SearchNewsBar(searchQuery) { query ->
                        searchQuery = query
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        items(filteredNews.size) { index ->
                            NewsItemView(filteredNews[index], onNewClick)
                            Divider()
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag(NEWS_SCREEN_EMPTY_LIST_TEST_TAG),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.no_news))
                }
            }
        }

        is NewsUiState.Error -> {
            Text(text = stringResource(R.string.text_error, uiState.message), modifier = Modifier.testTag(
                NEWS_SCREEN_ERROR_TEST_TAG))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsBar(searchQuery: TextFieldValue, onSearchQueryChanged: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 2.dp),
        label = { Text(text = stringResource(R.string.search_label)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun NewsItemView(newsItem: New, onNewClick: (Int) -> Unit) {
    Column(modifier = Modifier
        .padding(8.dp)
        .clickable { onNewClick(newsItem.id) }.testTag(NEWS_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG.plus("_${newsItem.id}"))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = newsItem.id.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(newsItem.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = newsItem.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}