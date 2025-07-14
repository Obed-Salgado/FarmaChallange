package dev.janus.farmachallange.ui.view.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.databinding.ItemLevelBinding

class ListLevelViewHolder(private val binding:ItemLevelBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(level:Nivel, selectLevel:(String) -> Unit){
        binding.tvNameLevel.text = level.nombre
        Glide.with(binding.root.context)
            .load(level.icono)
            .into(binding.ivIconLevel)
        binding.cvLevel.setCardBackgroundColor(Color.TRANSPARENT)
        itemView.setOnClickListener{
            selectLevel(level.id)
        }
    }
}