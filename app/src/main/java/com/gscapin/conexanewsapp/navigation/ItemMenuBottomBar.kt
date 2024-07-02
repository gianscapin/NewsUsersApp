package com.gscapin.conexanewsapp.navigation

import com.gscapin.conexanewsapp.R

sealed class ItemMenuBottomBar(
    val icon: Int,
    val title: String,
    val route: String
) {
    object NewsScreen : ItemMenuBottomBar(
        R.drawable.news,
        "Noticias",
        NewsScreens.HomeScreen.name
    )

    object UsersScreen : ItemMenuBottomBar(
        R.drawable.users,
        "Usuarios",
        NewsScreens.UsersScreen.name
    )

    object MapScreen : ItemMenuBottomBar(
        R.drawable.map,
        "Mapa",
        NewsScreens.MapScreen.name
    )
}