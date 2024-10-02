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
import com.example.rickmorticlassicview.domain.usecase.GetCharactersUseCase
import com.example.rickmorticlassicview.model.Character
import kotlinx.coroutines.flow.Flow



class CharactersViewModel(
    private val charactersUseCase: GetCharactersUseCase,
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




}