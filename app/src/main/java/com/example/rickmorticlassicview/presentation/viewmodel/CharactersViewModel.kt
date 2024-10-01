package com.example.rickmorticlassicview.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorticlassicview.domain.usecase.GetCharactersDetailUseCase
import com.example.rickmorticlassicview.domain.usecase.GetCharactersUseCase
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
typealias CharacterListState = ResourceState<List<Character>>
typealias CharacterDetailState = ResourceState<Character>

class CharactersViewModel(private val charactersUseCase: GetCharactersUseCase,
                          private val  characterDetailUseCase: GetCharactersDetailUseCase
) : ViewModel() {

    private val characterMutableLiveData = MutableLiveData<CharacterListState>()
    private val characterDetailMutableLiveData = MutableLiveData<CharacterDetailState>()
    private var characters: MutableList<Character> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    fun getCharacterLiveData(): LiveData<CharacterListState> {
        return characterMutableLiveData
    }

    fun getCharacterDetailLiveData(): LiveData<CharacterDetailState> {
        return characterDetailMutableLiveData
    }

    fun fetchCharacters(page: Int) {
        if (isLoading) return // Evita múltiples llamadas
        isLoading = true
        characterMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = charactersUseCase.execute(page)
                characters.addAll(data)

                withContext(Dispatchers.Main) {
                    characterMutableLiveData.value = ResourceState.Success(characters)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    characterMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
            finally {
                isLoading = false // Resetea la bandera de carga
            }
        }
    }

    fun fetchCharacter(characterId: Int) {
        characterDetailMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = characterDetailUseCase.execute(characterId)

                withContext(Dispatchers.Main) {

                        characterDetailMutableLiveData.value =ResourceState.Success(data)
                    }
                }
             catch (e: Exception) {
                 withContext(Dispatchers.Main) {
                     characterDetailMutableLiveData.value =
                         ResourceState.Error(e.localizedMessage.orEmpty())
                 }
            }
        }
    }

}