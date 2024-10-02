package com.example.rickmorticlassicview.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorticlassicview.data.remote.CharacterPagingSource
import com.example.rickmorticlassicview.domain.usecase.GetCharactersDetailUseCase
import com.example.rickmorticlassicview.domain.usecase.GetCharactersUseCase
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// typealias CharacterListState = ResourceState<List<Character>>
typealias CharacterDetailState = ResourceState<Character>

class CharactersViewModel(
    private val charactersUseCase: GetCharactersUseCase,
    private val characterDetailUseCase: GetCharactersDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentPageKey: String = "current_page"

    // Método para obtener el flujo de PagingData de personajes
    fun getPagedCharacters(): Flow<PagingData<Character>> {
        return Pager(PagingConfig(pageSize = 20)) {
            CharacterPagingSource(charactersUseCase, savedStateHandle.get<Int>(currentPageKey) ?: 1)
        }.flow.cachedIn(viewModelScope)
    }

    fun setCurrentPage(page: Int) {
        savedStateHandle[currentPageKey] = page
    }

    // LiveData para manejar los detalles de un personaje específico
    private val characterDetailMutableLiveData = MutableLiveData<CharacterDetailState>()

    fun getCharacterDetailLiveData(): LiveData<CharacterDetailState> {
        return characterDetailMutableLiveData
    }

    // Método para obtener los detalles de un personaje específico
    fun fetchCharacter(characterId: Int) {
        characterDetailMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = characterDetailUseCase.execute(characterId)

                withContext(Dispatchers.Main) {
                    characterDetailMutableLiveData.value = ResourceState.Success(data)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    characterDetailMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}