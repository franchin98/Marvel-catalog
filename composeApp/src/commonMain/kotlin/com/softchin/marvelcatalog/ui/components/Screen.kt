package com.softchin.marvelcatalog.ui.components

import marvelcatalog.composeapp.generated.resources.Res
import marvelcatalog.composeapp.generated.resources.ic_iron_man
import marvelcatalog.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.DrawableResource

sealed class Screen(val route: String, val drawableResource: DrawableResource){
    data object HomeScreen: Screen("HomeScreen", Res.drawable.ic_iron_man)

    data object SearchScreen: Screen("SearchScreen", Res.drawable.ic_search)

}