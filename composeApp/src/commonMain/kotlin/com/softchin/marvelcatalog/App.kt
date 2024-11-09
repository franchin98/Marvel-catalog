package com.softchin.marvelcatalog

import androidx.compose.animation.slideInHorizontally
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softchin.marvelcatalog.ui.components.BarNavigation
import com.softchin.marvelcatalog.ui.components.Screen
import com.softchin.marvelcatalog.ui.screens.HomeScreen
import com.softchin.marvelcatalog.ui.screens.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()

            Scaffold(bottomBar = { BarNavigation(navController = navController) }) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                ) {
                    composable(
                        Screen.HomeScreen.route,
                        enterTransition = { slideInHorizontally() })
                    { HomeScreen() }

                    composable(
                        Screen.SearchScreen.route,
                        enterTransition = { slideInHorizontally() })
                    { SearchScreen() }
                }
            }
        }
    }
}




