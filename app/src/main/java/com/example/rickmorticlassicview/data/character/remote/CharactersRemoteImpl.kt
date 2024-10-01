package com.example.rickmorticlassicview.data.character.remote

import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.model.Character

class CharactersRemoteImpl (private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(): List<Character>{
        return rickAndMortyService.getCharacters().characters
    }

    suspend fun getCharacter( characterId: Int): Character{
        return  rickAndMortyService.getCharacter(characterId)
    }

}