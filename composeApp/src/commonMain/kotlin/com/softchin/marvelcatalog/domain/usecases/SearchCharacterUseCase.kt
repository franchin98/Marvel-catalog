package com.softchin.marvelcatalog.domain.usecases

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchCharacterUseCase {

    suspend fun searchCharactersByName(name: String): Flow<List<Character>>

}