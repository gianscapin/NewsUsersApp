package com.gscapin.conexanewsapp.di

import com.gscapin.conexanewsapp.network.JsonPlaceholderApi
import com.gscapin.conexanewsapp.repository.NewRepository
import com.gscapin.conexanewsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewRepository(api: JsonPlaceholderApi) = NewRepository(api)

    @Singleton
    @Provides
    fun provideJsonPlaceholderApi(): JsonPlaceholderApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JsonPlaceholderApi::class.java)
}