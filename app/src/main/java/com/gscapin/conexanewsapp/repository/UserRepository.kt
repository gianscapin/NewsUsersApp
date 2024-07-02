package com.gscapin.conexanewsapp.repository

import com.gscapin.conexanewsapp.network.JsonPlaceholderApi
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: JsonPlaceholderApi) {
    suspend fun getUsers() = api.getUsers()
}