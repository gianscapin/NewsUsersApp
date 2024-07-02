package com.gscapin.conexanewsapp.repository

import com.gscapin.conexanewsapp.network.JsonPlaceholderApi
import javax.inject.Inject

class NewRepository @Inject constructor(private val api: JsonPlaceholderApi) {
    suspend fun getNews() = api.getNews()
}