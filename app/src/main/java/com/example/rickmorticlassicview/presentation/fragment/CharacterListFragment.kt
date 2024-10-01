package com.example.rickmorticlassicview.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorticlassicview.R
import com.example.rickmorticlassicview.presentation.adapter.CharacterListAdapter
import com.example.rickmorticlassicview.databinding.FragmentCharacterListBinding
import com.example.rickmorticlassicview.model.ResourceState
import com.example.rickmorticlassicview.presentation.viewmodel.CharactersViewModel
import com.example.rickmorticlassicview.presentation.viewmodel.CharacterListState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CharacterListFragment : Fragment() {

    private val binding: FragmentCharacterListBinding by lazy {
        FragmentCharacterListBinding.inflate(layoutInflater)
    }

    private val characterListAdapter = CharacterListAdapter()

    private val characterViewModel: CharactersViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUI()

        // Carga inicial de personajes
        characterViewModel.fetchCharacters(currentPage)

        // Listener para el scroll
        binding.rvCharacterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && !isLastPage && !binding.rvCharacterList.canScrollVertically(1)) {
                    isLoading = true
                    currentPage++
                    characterViewModel.fetchCharacters(currentPage)
                }
                binding.btnScrollToTop.visibility = if (recyclerView.canScrollVertically(-1)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        })
        // Click del botón para desplazarse al inicio
        binding.btnScrollToTop.setOnClickListener {
            binding.rvCharacterList.smoothScrollToPosition(0)
        }
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
                if (state.result.isEmpty()) {
                    isLastPage = true
                    // Si la lista de resultados está vacía y es la última página, muestra un mensaje
                    Snackbar.make(
                        binding.rvCharacterList,
                        "No hay más personajes para cargar.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    characterListAdapter.submitList(state.result)
                    isLastPage = false
                }
                isLoading = false // Resetea la bandera de carga
            }

            is ResourceState.Error -> {
                binding.pbCharacterList.visibility = View.GONE
                showErrorDialog(state.error)
                isLoading = false // Resetea la bandera de carga
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
                characterViewModel.fetchCharacters(currentPage)
            }
            .show()
    }

}
