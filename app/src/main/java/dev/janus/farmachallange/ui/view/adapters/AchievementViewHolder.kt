package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.ItemAchievementBinding
import dev.janus.farmachallange.utils.loadImage

class AchievementViewHolder(private val binding: ItemAchievementBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(level: Level?, selectIcon: (String) -> Unit) {
        if(level == null){
            binding.ivIconLevel.loadImage("https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/levelsIcons%2Fconceptos_generales.png?alt=media&token=c0bd073f-d970-4ace-bbdb-b7d170439702")
            //binding.content.setCardBackgroundColor(itemView.context.getColor(R.color.white))
        }

//
//        this.itemView.setOnClickListener {
//            selectIcon(level.id)
//        }
    }
}