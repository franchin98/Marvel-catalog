package com.softchin.marvelcatalog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.ui.components.CharacterItem
import com.softchin.marvelcatalog.ui.viewmodels.MainViewModel
import com.softchin.marvelcatalog.ui.viewmodels.UiState
import org.koin.compose.viewmodel.koinViewModel

// Número que establece la cantidad de columnas en la grilla
const val TWO_COLUMNS = 2

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    val uiState by viewModel.homeUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            ShowContent(
                (uiState as UiState.Success).characters,
                isRefreshing = isLoading,
                onRefresh = { viewModel.loadNewCharacters() })
        }

        is UiState.Error -> {
            ShowErrorMessage((uiState as UiState.Error).message!!)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShowContent(characters: List<Character>, isRefreshing: Boolean, onRefresh: () -> Unit) {

    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { onRefresh() })

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(red = 115, green = 16, blue = 16),
                        Color.Black
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        PullRefreshIndicator(
            refreshing = isRefreshing, state = refreshState, modifier = Modifier.align(
                Alignment.TopCenter
            ).zIndex(100F),
            contentColor = Color.Red
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(TWO_COLUMNS),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 30.dp, bottom = 65.dp).pullRefresh(refreshState)
        ) {
            items(characters) { character ->
                CharacterItem(character)
            }
        }
    }
}

@Composable
private fun ShowErrorMessage(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = message)
    }
}

@Composable
private fun ShowLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = Color.Red,
        )
    }
}