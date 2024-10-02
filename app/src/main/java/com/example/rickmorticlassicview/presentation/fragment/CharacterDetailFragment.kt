package com.example.rickmorticlassicview.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickmorticlassicview.R
import com.example.rickmorticlassicview.databinding.FragmentCharacterDetailBinding
import com.example.rickmorticlassicview.model.ResourceState
import com.example.rickmorticlassicview.presentation.viewmodel.CharactersViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import com.example.rickmorticlassicview.model.Character
import com.example.rickmorticlassicview.presentation.viewmodel.CharacterDetailState

class CharacterDetailFragment : Fragment() {

    private val binding: FragmentCharacterDetailBinding by lazy {
        FragmentCharacterDetailBinding.inflate(layoutInflater)
    }

    private val args: CharacterDetailFragmentArgs by navArgs()

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

        characterViewModel.fetchCharacter(args.characterId)
    }

    private fun initViewModel() {

        characterViewModel.getCharacterDetailLiveData().observe(viewLifecycleOwner) { state ->
            handleCharacterDetailState(state)
        }
    }

    private fun handleCharacterDetailState(state: CharacterDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbCjaracterDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbCjaracterDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbCjaracterDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI(character: Character) {
        binding.tvCharacterDetailName.text = character.name
        binding.tvCharacterDetailSpecie.text = character.species.name

        Glide.with(requireContext())
            .load(character.image)
            .into(binding.ivCharacterDetail)
    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_message)
            .setMessage(error)
            .setPositiveButton(R.string.ok_action, null)
            .setNegativeButton(R.string.retry_action) { dialog, witch ->
                characterViewModel.getPagedCharacters()
            }
            .show()
    }
}