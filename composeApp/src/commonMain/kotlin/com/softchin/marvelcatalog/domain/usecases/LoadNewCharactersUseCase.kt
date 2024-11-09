package com.softchin.marvelcatalog.domain.usecases

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LoadNewCharactersUseCase {
    suspend fun loadNewCharacters(): Flow<List<Character>>
}