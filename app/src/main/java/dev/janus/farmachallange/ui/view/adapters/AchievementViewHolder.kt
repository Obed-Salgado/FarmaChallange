package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.ItemAchievementBinding

class AchievementViewHolder(private val binding: ItemAchievementBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(level: Level, selectIcon: (String) -> Unit) {
//        binding.ivIconLevel.loadImage(level.icono)
//
//        this.itemView.setOnClickListener {
//            selectIcon(level.id)
//        }
    }
}