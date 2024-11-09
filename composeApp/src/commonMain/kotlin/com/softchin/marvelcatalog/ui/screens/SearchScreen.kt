package com.softchin.marvelcatalog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.ui.components.CharacterSearchContainer
import com.softchin.marvelcatalog.ui.components.SearchbarCharacter
import com.softchin.marvelcatalog.ui.viewmodels.SearchUiState
import com.softchin.marvelcatalog.ui.viewmodels.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

    val searchViewModel = koinViewModel<SearchViewModel>()
    var query by remember { mutableStateOf("") }

    val searchUiState by searchViewModel.searchUiState.collectAsState()

    Column(
        modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black, Color(red = 115, green = 16, blue = 16),
                        Color.Black
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(contentAlignment = Alignment.TopCenter) {
            SearchbarCharacter(
                query = query,
                onValueChange = { newText -> query = newText },
                clearQuery = { query = "" }
            )
        }

        if (query.isNotEmpty()) {
            searchViewModel.searchCharactersByNameStartsWith(name = query.lowercase())

            when (searchUiState) {
                is SearchUiState.Loading -> {
                    CircularProgressIndicator(color = Color.Red)
                }

                is SearchUiState.Error -> {}
                is SearchUiState.Success -> {
                    val characters = (searchUiState as SearchUiState.Success).characters
                    ShowCharactersResult(characters)
                }
            }

        } else {
            Text(
                text = "Tus resultados de búsqueda aparecerán aquí",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

    }
}

@Composable
fun ShowCharactersResult(characters: List<Character>) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp)) {
        items(characters) { character ->
            CharacterSearchContainer(character = character, modifier = Modifier.fillMaxWidth())
        }
    }

}
