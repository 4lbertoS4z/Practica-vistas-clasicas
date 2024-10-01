package com.example.rickmorticlassicview.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorticlassicview.R
import com.example.rickmorticlassicview.presentation.adapter.CharacterListAdapter
import com.example.rickmorticlassicview.databinding.FragmentCharacterListBinding
import com.example.rickmorticlassicview.model.ResourceState
import com.example.rickmorticlassicview.presentation.viewmodel.CharactersViewModel
import com.example.rickmorticlassicview.presentation.viewmodel.CharacterListState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CharacterListFragment : Fragment() {

    private val binding: FragmentCharacterListBinding by lazy {
        FragmentCharacterListBinding.inflate(layoutInflater)
    }

    private val characterListAdapter = CharacterListAdapter()

    private val characterViewModel : CharactersViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUI()

        characterViewModel.fetchCharacters()
    }

    private fun initViewModel() {

        characterViewModel.getCharacterLiveData().observe(viewLifecycleOwner) { state ->
            handleCharacterListState(state)
        }
    }

    private fun handleCharacterListState(state: CharacterListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbCharacterList.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbCharacterList.visibility = View.GONE
                characterListAdapter.submitList(state.result)
            }

            is ResourceState.Error -> {
                binding.pbCharacterList.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI() {
        binding.rvCharacterList.adapter = characterListAdapter
        binding.rvCharacterList.layoutManager = LinearLayoutManager(requireContext())

        characterListAdapter.onClickListener = { character ->
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    character.id.toInt()
                )
            )
        }
    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_message)
            .setMessage(error)
            .setPositiveButton(R.string.ok_action, null)
            .setNegativeButton(R.string.retry_action) { dialog, witch ->
                characterViewModel.fetchCharacters()
            }
            .show()
    }

}