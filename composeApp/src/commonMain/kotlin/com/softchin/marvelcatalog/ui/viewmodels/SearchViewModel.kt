package com.softchin.marvelcatalog.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.domain.usecases.SearchCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Success(val characters: List<Character>) : SearchUiState

    data class Error(val message: String) : SearchUiState
}

class SearchViewModel(private val searchCharacterUseCase: SearchCharacterUseCase) : ViewModel() {

    private val _searchUiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Loading)

    val searchUiState = _searchUiState.asStateFlow()

    fun searchCharactersByNameStartsWith(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchCharacterUseCase.searchCharactersByName(name)
                .catch { error ->
                    _searchUiState.value = SearchUiState.Error(message = error.message.orEmpty())
                }.collect { characters ->
                    _searchUiState.value = SearchUiState.Success(characters = characters)
                }
        }
    }

}