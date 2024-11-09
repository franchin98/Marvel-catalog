package com.softchin.marvelcatalog.di

import com.softchin.marvelcatalog.data.network.PublicKeyInterceptor
import com.softchin.marvelcatalog.data.repository.CharacterApiRepository
import com.softchin.marvelcatalog.data.repository.CharacterLocalRepository
import com.softchin.marvelcatalog.data.repository.CharacterNetworkRepository
import com.softchin.marvelcatalog.data.repository.CharacterSqldelightRepository
import com.softchin.marvelcatalog.domain.service.CharacterService
import com.softchin.marvelcatalog.domain.usecases.GetCharacterUseCase
import com.softchin.marvelcatalog.domain.usecases.LoadNewCharactersUseCase
import com.softchin.marvelcatalog.domain.usecases.SearchCharacterUseCase
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<CharacterLocalRepository> { CharacterSqldelightRepository(androidContext(), get()) }
    single<CharacterNetworkRepository> { CharacterApiRepository() }
    single<GetCharacterUseCase> {
        CharacterService(
            characterLocalRepo = get(),
            characterNetworkRepo = get(),
        )
    }
    single<LoadNewCharactersUseCase> {
        CharacterService(
            characterLocalRepo = get(),
            characterNetworkRepo = get()
        )
    }
    single<SearchCharacterUseCase> {
        CharacterService(
            characterLocalRepo = get(),
            characterNetworkRepo = get(),
        )
    }

    single<Interceptor> { PublicKeyInterceptor() }
}