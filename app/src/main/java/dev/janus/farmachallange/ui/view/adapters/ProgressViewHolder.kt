package dev.janus.farmachallange.ui.view.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.databinding.ItemProgressBinding

class ProgressViewHolder (private val binding: ItemProgressBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(progress: Progress){
        binding.tvLevel.text = progress.title
        binding.tvDescription.text = progress.description
        binding.progressBar.progress = (progress.progress * 100).toInt()
        Glide.with(binding.root.context)
            .load(progress.icon)
            .into(binding.ivIconLevel)
        binding.cvProgress.setCardBackgroundColor(Color.TRANSPARENT)
    }
}