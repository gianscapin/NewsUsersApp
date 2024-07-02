package com.gscapin.conexanewsapp.screens.newDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gscapin.conexanewsapp.R
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.utils.NEWS_DETAIL_SCREEN_TEST_TAG


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDetailsScreen(newsItem: New?, goBackToHome: () -> Unit) {
    newsItem?.let { new ->
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = stringResource(R.string.topbar_title_news_details))},
                    navigationIcon = {
                        IconButton(onClick = goBackToHome) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to home")
                        }
                    })
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).testTag(NEWS_DETAIL_SCREEN_TEST_TAG)) {
                Text(text = new.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(new.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = new.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = new.content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}