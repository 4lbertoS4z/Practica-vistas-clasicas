package com.example.rickmorticlassicview.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorticlassicview.presentation.adapter.CharacterListAdapter
import com.example.rickmorticlassicview.databinding.FragmentCharacterListBinding
import com.example.rickmorticlassicview.presentation.viewmodel.CharactersViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private val characterViewModel: CharactersViewModel by activityViewModel()
    private val pagingAdapter: CharacterListAdapter by lazy {
        CharacterListAdapter { character ->
            val currentPage = (pagingAdapter.itemCount + 41) / 42
            characterViewModel.setCurrentPage(currentPage)
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    character.id.toInt()
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initViewModel()
    }

    private fun initUI() {
        binding.rvCharacterList.adapter = pagingAdapter
        binding.rvCharacterList.layoutManager = LinearLayoutManager(requireContext())

        binding.btnScrollToTop.setOnClickListener {
            binding.rvCharacterList.smoothScrollToPosition(0)
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.pbCharacterList.visibility = if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE

            if (loadState.refresh is LoadState.Error) {
                val error = (loadState.refresh as LoadState.Error).error
                Snackbar.make(binding.rvCharacterList, error.localizedMessage.orEmpty(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            characterViewModel.getPagedCharacters().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }
}