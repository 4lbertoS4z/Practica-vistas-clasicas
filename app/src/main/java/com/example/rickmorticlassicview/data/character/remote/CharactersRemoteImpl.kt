package com.example.rickmorticlassicview.data.character.remote

import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse

class CharactersRemoteImpl (private val rickAndMortyService: RickAndMortyService) :
    CharactersRepository {

    override suspend fun getCharacters(page: Int): List<Character> {
        val response: CharactersResponse = rickAndMortyService.getCharacters(page)
        return response.characters
    }

    override suspend fun getCharacter(characterId: Int): Character {
        return rickAndMortyService.getCharacter(characterId)
    }
}