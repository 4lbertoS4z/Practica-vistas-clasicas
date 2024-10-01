package com.example.rickmorticlassicview.domain.usecase

import com.example.rickmorticlassicview.domain.CharactersRepository
import com.example.rickmorticlassicview.model.Character

class GetCharactersDetailUseCase(private val charactersRepository: CharactersRepository) {

    suspend fun execute(characterId:Int): Character {
        return charactersRepository.getCharacter(characterId)
    }

}