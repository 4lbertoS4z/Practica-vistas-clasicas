package com.example.rickmorticlassicview.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.domain.usecase.GetCharactersUseCase

class CharacterPagingSource(
    private val repository: GetCharactersUseCase,
    private val initialPage : Int =0 // Cambiar a usar el valor inicial de página
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: initialPage
            val response = repository.execute(page)

            LoadResult.Page(
                data = response.characters,  // Lista de personajes
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.pageInfo.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}