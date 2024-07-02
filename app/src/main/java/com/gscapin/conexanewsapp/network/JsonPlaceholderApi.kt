package com.gscapin.conexanewsapp.network

import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.domain.model.User
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface JsonPlaceholderApi {

    @GET("posts")
    suspend fun getNews(): List<New>

    @GET("users")
    suspend fun getUsers(): List<User>
}