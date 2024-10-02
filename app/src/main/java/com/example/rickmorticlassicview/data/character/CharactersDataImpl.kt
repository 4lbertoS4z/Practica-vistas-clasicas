package com.example.rickmorticlassicview.data.character

import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.CharactersResponse


class CharactersDataImpl(private val charactersRemoteImpl: RickAndMortyService): CharactersRepository {


    override suspend fun getCharacters(page: Int): CharactersResponse {
        return charactersRemoteImpl.getCharacters(page)
    }


}