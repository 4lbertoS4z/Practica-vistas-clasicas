package com.example.rickmorticlassicview.domain
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse

interface CharactersRepository {
    suspend fun getCharacters(page: Int): CharactersResponse
}