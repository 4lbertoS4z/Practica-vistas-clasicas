package com.example.rickmorticlassicview.domain.usecase

import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse

class GetCharactersUseCase(private val charactersRepository: CharactersRepository) {

    suspend fun execute(page: Int): CharactersResponse {
        return charactersRepository.getCharacters(page)
    }
}