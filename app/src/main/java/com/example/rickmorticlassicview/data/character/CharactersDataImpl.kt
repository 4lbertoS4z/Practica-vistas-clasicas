package com.example.rickmorticlassicview.data.character

import com.example.rickmorticlassicview.data.character.remote.CharactersRemoteImpl
import com.example.rickmorticlassicview.data.remote.RickAndMortyService
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse


class CharactersDataImpl(private val charactersRemoteImpl: RickAndMortyService): CharactersRepository {


    override suspend fun getCharacters(page: Int): List<Character> {
        return charactersRemoteImpl.getCharacters(page).characters
    }

    override suspend fun getCharacter(characterId: Int): Character {
        return charactersRemoteImpl.getCharacter(characterId)
    }
}