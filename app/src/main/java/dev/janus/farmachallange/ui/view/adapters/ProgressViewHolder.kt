package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.databinding.ItemProgressBinding

class ProgressViewHolder (private val binding: ItemProgressBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(item: Int){
        binding.tvEtapa.text = "${binding.tvEtapa.text}$item"
    }
}