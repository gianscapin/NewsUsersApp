package com.gscapin.conexanewsapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gscapin.conexanewsapp.navigation.NewsScreens
import com.gscapin.conexanewsapp.presentation.home.HomeViewModel
import com.gscapin.conexanewsapp.presentation.user.UserViewModel
import com.gscapin.conexanewsapp.screens.home.HomeScreen
import com.gscapin.conexanewsapp.screens.map.MapScreen
import com.gscapin.conexanewsapp.screens.newDetails.NewDetailsScreen
import com.gscapin.conexanewsapp.screens.user.UsersScreen

@Composable
fun Navigation(navController: NavHostController) {
    val homeViewModel = viewModel<HomeViewModel>()
    val userViewModel = viewModel<UserViewModel>()
    NavHost(navController = navController, startDestination = NewsScreens.HomeScreen.name) {
        composable(NewsScreens.HomeScreen.name) {
            val homeViewModelUiState by homeViewModel.uiState.collectAsState()
            HomeScreen(
                onNewClick = { id ->
                    navController.navigate(NewsScreens.NewDetailsScreen.name + "/$id")
                },
                uiState = homeViewModelUiState
            )
        }

        val detailName = NewsScreens.NewDetailsScreen.name
        composable(
            route = "$detailName/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("newsId")?.let {
                val newDetail = it.let { id ->  homeViewModel.getNewsById(id)}
                NewDetailsScreen(newsItem = newDetail) {
                    navController.popBackStack()
                }
            }
        }

        composable(NewsScreens.UsersScreen.name){
            val userViewModelUiState by userViewModel.uiState.collectAsState()
            UsersScreen(userUiState = userViewModelUiState) { lat, lng, username ->
                navController.navigate(NewsScreens.MapScreen.name + "/$lat/$lng/$username")
            }
        }

        val mapScreenName = NewsScreens.MapScreen.name
        composable(route = "$mapScreenName/{lat}/{lng}/{username}",
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType},
                navArgument("lng") { type = NavType.StringType},
                navArgument("username") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat")
            val lng = backStackEntry.arguments?.getString("lng")
            val username = backStackEntry.arguments?.getString("username")
            MapScreen(latitude = lat, longitude = lng, username = username) {
                navController.popBackStack()
            }
        }
    }
}