package com.softchin.marvelcatalog.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.painterResource

@Composable
fun BarNavigation(modifier: Modifier = Modifier, navController: NavController) {

    val bottomNavItems = listOf(Screen.HomeScreen, Screen.SearchScreen)

    BottomNavigation(backgroundColor = Color.Black) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { bottomItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == bottomItem.route } == true
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(route = bottomItem.route) {
                        navController.graph.findStartDestination().route?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(bottomItem.drawableResource),
                        contentDescription = "Icono de navegación",
                        modifier = Modifier.size(30.dp),
                        tint = if (isSelected) Color(
                            red = 209,
                            green = 5,
                            blue = 0
                        ) else Color.White
                    )
                }
            )
        }

    }
}