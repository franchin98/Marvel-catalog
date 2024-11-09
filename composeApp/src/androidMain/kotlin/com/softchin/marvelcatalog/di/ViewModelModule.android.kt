package com.softchin.marvelcatalog.di

import com.softchin.marvelcatalog.ui.viewmodels.MainViewModel
import com.softchin.marvelcatalog.ui.viewmodels.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { MainViewModel(characterGetter = get(), loadNewCharactersUseCase = get()) }
    viewModel { SearchViewModel(searchCharacterUseCase = get()) }
}