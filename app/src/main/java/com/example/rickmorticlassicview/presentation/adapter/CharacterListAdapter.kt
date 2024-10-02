package com.example.rickmorticlassicview.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorticlassicview.databinding.RowCharacterItemBinding
import com.example.rickmorticlassicview.model.Character




class CharacterListAdapter(
    private val onClickListener: (Character) -> Unit
) : PagingDataAdapter<Character, CharacterListAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = RowCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let {
            holder.bind(it, onClickListener)
        }
    }

    inner class CharacterViewHolder(private val binding: RowCharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, onClickListener: (Character) -> Unit) {
            binding.tvCharacterItemName.text = character.name
            Glide.with(binding.ivCharacterItem.context)
                .load(character.image)
                .into(binding.ivCharacterItem)

            // Configura el click listener
            binding.root.setOnClickListener {
                onClickListener(character)
            }
        }
    }
}

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}