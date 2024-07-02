package com.gscapin.conexanewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gscapin.conexanewsapp.navigation.ItemMenuBottomBar
import com.gscapin.conexanewsapp.navigation.NewsScreens
import com.gscapin.conexanewsapp.screens.Navigation
import com.gscapin.conexanewsapp.ui.theme.ConexaNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConexaNewsAppTheme {
                NewsApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val itemsScreen = listOf(
            ItemMenuBottomBar.NewsScreen,
            ItemMenuBottomBar.UsersScreen
        )

        Scaffold(
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Navigation(navController = navController)
                }
            },
            bottomBar = {
                val variable = navBackStackEntry?.destination?.route
                when (navBackStackEntry?.destination?.route) {
                    NewsScreens.NewDetailsScreen.name -> {}
                    else -> {
                        NavigationBar {
                            itemsScreen.forEachIndexed { index, itemMenuBottomBar ->
                                NavigationBarItem(
                                    selected = navController.currentDestination?.route?.startsWith(
                                        itemMenuBottomBar.route
                                    ) == true,
                                    onClick = {
                                        if (navController.currentDestination?.route != itemMenuBottomBar.route) {
                                            navController.navigate(itemMenuBottomBar.route)
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = itemMenuBottomBar.icon),
                                            contentDescription = itemMenuBottomBar.title
                                        )
                                    },
                                    label = {
                                        Text(text = itemMenuBottomBar.title)
                                    },
                                    alwaysShowLabel = true
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}