package com.example.rickmorticlassicview.data.remote

import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(): CharactersResponse

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): Character
}