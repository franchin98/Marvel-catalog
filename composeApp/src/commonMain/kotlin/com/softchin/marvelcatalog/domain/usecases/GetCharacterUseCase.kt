package com.softchin.marvelcatalog.domain.usecases

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface GetCharacterUseCase {

    suspend fun getCharacters() : Flow<List<Character>>

}