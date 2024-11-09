package com.softchin.marvelcatalog.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.domain.usecases.GetCharacterUseCase
import com.softchin.marvelcatalog.domain.usecases.LoadNewCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


sealed interface UiState {

    data object Loading : UiState

    data class Success(val characters: List<Character>) : UiState

    data class Error(val message: String?) : UiState

}

class MainViewModel(private val characterGetter: GetCharacterUseCase, private val loadNewCharactersUseCase: LoadNewCharactersUseCase) : ViewModel() {

    private val _homeUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getCharacters()
    }

    fun loadNewCharacters() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            loadNewCharactersUseCase.loadNewCharacters().catch {
                _homeUiState.value = UiState.Error(message = it.message)
            }.collect { newCharacters ->
                _homeUiState.value = UiState.Success(characters = newCharacters)
                _isLoading.value = false
            }
        }
    }


    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterGetter.getCharacters().catch { error ->
                _homeUiState.value = UiState.Error(message = error.message)

            }.collect { characters ->
                _homeUiState.value = UiState.Success(characters = characters)
            }
        }
    }
}