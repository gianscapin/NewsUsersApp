package com.gscapin.conexanewsapp.screens.user

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gscapin.conexanewsapp.R
import com.gscapin.conexanewsapp.domain.model.User
import com.gscapin.conexanewsapp.presentation.user.UserUiState
import com.gscapin.conexanewsapp.utils.USERS_SCREEN_EMPTY_LIST_TEST_TAG
import com.gscapin.conexanewsapp.utils.USERS_SCREEN_ERROR_TEST_TAG
import com.gscapin.conexanewsapp.utils.USERS_SCREEN_LOADING_TEST_TAG
import com.gscapin.conexanewsapp.utils.USERS_SCREEN_SUCCESS_TEST_TAG

@Composable
fun UsersScreen(userUiState: UserUiState, onUserClick: (String, String, String) -> Unit) {

    when (userUiState) {
        is UserUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag(USERS_SCREEN_LOADING_TEST_TAG),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UserUiState.Success -> {
            if (userUiState.users.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .testTag(USERS_SCREEN_SUCCESS_TEST_TAG)
                ) {
                    items(userUiState.users.size) { index ->
                        UserContent(userUiState.users[index], onUserClick)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag(USERS_SCREEN_EMPTY_LIST_TEST_TAG),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.text_no_user))
                }
            }
        }

        is UserUiState.Error -> {
            Text(text = stringResource(R.string.text_error, userUiState.message), modifier = Modifier.testTag(
                USERS_SCREEN_ERROR_TEST_TAG))
        }
    }
}

@Composable
private fun UserContent(
    user: User,
    onUserClick: (String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = " ${user.firstname} ${user.lastname}", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.text_users_city, user.address.city), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(R.string.text_users_company, user.company.name), style = MaterialTheme.typography.bodySmall)
            }

            TextButton(onClick = { onUserClick(user.address.geo.lat, user.address.geo.lng, "${user.firstname} ${user.lastname}") }) {
                Text(text = stringResource(R.string.text_button_go_to_map))
            }
        }
    }
}