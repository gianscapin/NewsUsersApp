package com.gscapin.conexanewsapp.domain.useCases

import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.repository.NewRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewRepository) {
    suspend operator fun invoke(): List<New> = repository.getNews()
}