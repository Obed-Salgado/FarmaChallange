package dev.janus.farmachallange.ui.view.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.ItemLevelBinding
import dev.janus.farmachallange.utils.loadImage

class ListLevelViewHolder(private val binding:ItemLevelBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(level:Level, selectLevel:(String) -> Unit) {
        binding.tvNameLevel.text = level.nombre
        binding.ivIconLevel.loadImage(level.icono)
        binding.cvLevel.setCardBackgroundColor(Color.TRANSPARENT)
        itemView.setOnClickListener{
            selectLevel(level.id)
        }
    }
}