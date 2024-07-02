package com.gscapin.conexanewsapp.navigation

enum class NewsScreens {
    HomeScreen,
    NewDetailsScreen,
    UsersScreen,
    MapScreen;

    companion object {
        fun fromRoute(route: String): NewsScreens =
            when(route?.substringBefore("/")) {
                HomeScreen.name -> HomeScreen
                NewDetailsScreen.name -> NewDetailsScreen
                UsersScreen.name -> UsersScreen
                MapScreen.name -> MapScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}