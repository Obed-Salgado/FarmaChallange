package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.databinding.ItemAchievementBinding

class AchievementViewHolder(private val binding: ItemAchievementBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(level: Nivel, selectIcon: (String) -> Unit){
//        Glide.with(binding.root.context)
//            .load(level.icono)
//            .into(binding.ivIconLevel)
//
//        this.itemView.setOnClickListener {
//            selectIcon(level.id)
//        }
    }
}