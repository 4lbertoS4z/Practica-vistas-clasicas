package com.example.rickmorticlassicview.data.remote

import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse


}