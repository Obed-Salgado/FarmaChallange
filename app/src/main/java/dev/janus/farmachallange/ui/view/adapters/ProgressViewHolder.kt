package dev.janus.farmachallange.ui.view.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.databinding.ItemProgressBinding
import dev.janus.farmachallange.utils.loadImage

class ProgressViewHolder (private val binding: ItemProgressBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(progress: Progress){
        binding.tvLevel.text = progress.title
        binding.tvDescription.text = progress.description
        binding.progressBar.progress = (progress.progress * 100).toInt()
        binding.ivIconLevel.loadImage(progress.icon)
        binding.cvProgress.setCardBackgroundColor(Color.TRANSPARENT)
    }
}