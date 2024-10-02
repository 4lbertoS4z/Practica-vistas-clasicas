package com.example.rickmorticlassicview.di

import com.example.rickmorticlassicview.data.character.CharactersDataImpl
import com.example.rickmorticlassicview.data.character.remote.CharactersRemoteImpl
import com.example.rickmorticlassicview.data.remote.ApiClient
import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.domain.usecase.GetCharactersUseCase
import com.example.rickmorticlassicview.presentation.viewmodel.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single<RickAndMortyService> { ApiClient.retrofit.create(RickAndMortyService::class.java) }
}

val charactersModule = module {
    factory { CharactersRemoteImpl(get()) }
    factory<CharactersRepository> { CharactersDataImpl(get()) }
    factory { GetCharactersUseCase(get()) }
    viewModel { CharactersViewModel(get(),get()) }
}