package com.gscapin.conexanewsapp.domain.useCases

import com.gscapin.conexanewsapp.domain.model.User
import com.gscapin.conexanewsapp.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(): List<User> = repository.getUsers()
}