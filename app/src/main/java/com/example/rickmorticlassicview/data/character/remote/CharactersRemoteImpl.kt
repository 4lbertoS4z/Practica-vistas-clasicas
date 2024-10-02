package com.example.rickmorticlassicview.data.character.remote

import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.CharactersResponse

class CharactersRemoteImpl (private val rickAndMortyService: RickAndMortyService) :
    CharactersRepository {

    override suspend fun getCharacters(page: Int): CharactersResponse {
        return rickAndMortyService.getCharacters(page)

    }


}