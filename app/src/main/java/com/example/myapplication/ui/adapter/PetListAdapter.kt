package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.databinding.PetItemLayoutBinding
import com.example.myapplication.ui.callback.PetListCallback

class PetListAdapter(private val petListCallback: PetListCallback) :
    RecyclerView.Adapter<PetListAdapter.PetViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val itemList = mutableListOf<AnimalData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = PetItemLayoutBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* root = */ parent,
            /* attachToRoot = */ false
        )
        return PetViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].id
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        itemList[position].let { pet ->
            holder.binding.model = pet
            holder.binding.root.setOnClickListener { petListCallback.onPetItemClick(pet) }
        }
    }

    fun updateItems(items: List<AnimalData>) {
        with(itemList) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    /**
     * Extremely minimal data displayed; todo: should be extended to provide more info
     */
    class PetViewHolder(val binding: PetItemLayoutBinding) : ViewHolder(binding.root)
}