package com.gscapin.conexanewsapp.domain.model

data class User(
    val address: Address,
    val birthDate: String,
    val company: Company,
    val email: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val login: Login,
    val phone: String,
    val website: String
)

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class Company(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

data class Login(
    val md5: String,
    val password: String,
    val registered: String,
    val sha1: String,
    val username: String,
    val uuid: String
)

data class Geo(
    val lat: String,
    val lng: String
)