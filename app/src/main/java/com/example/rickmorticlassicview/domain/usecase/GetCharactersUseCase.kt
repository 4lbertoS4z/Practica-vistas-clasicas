package com.example.rickmorticlassicview.domain.usecase

import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character

class GetCharactersUseCase(private val charactersRepository: CharactersRepository) {

    suspend fun execute(page: Int): List<Character> {
        return charactersRepository.getCharacters(page)
    }
}