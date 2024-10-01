package com.example.rickmorticlassicview.data.character

import com.example.rickmorticlassicview.data.character.remote.CharactersRemoteImpl
import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character


class CharactersDataImpl(private val charactersRemoteImpl: CharactersRemoteImpl): CharactersRepository {
    override suspend fun getCharacters(): List<Character> {
        return  charactersRemoteImpl.getCharacters()
    }

    override suspend fun getCharacter(characterId: Int): Character {
        return charactersRemoteImpl.getCharacter(characterId)
    }
}