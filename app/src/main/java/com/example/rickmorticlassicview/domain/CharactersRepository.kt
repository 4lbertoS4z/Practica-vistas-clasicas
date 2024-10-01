package com.example.rickmorticlassicview.domain
import com.example.rickmorticlassicview.model.Character
interface CharactersRepository {
    suspend fun getCharacters(): List<Character>

    suspend fun getCharacter( characterId: Int): Character
}