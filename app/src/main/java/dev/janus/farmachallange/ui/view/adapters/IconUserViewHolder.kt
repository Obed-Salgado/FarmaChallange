package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.databinding.ItemIconUserBinding
import dev.janus.farmachallange.utils.loadImage

class IconUserViewHolder(private val binding: ItemIconUserBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(url: String, selectIcon: (String) -> Unit, dismiss: () -> Unit) {
        binding.ivIconUser.loadImage(url)

        this.itemView.setOnClickListener {
            selectIcon(url)
            dismiss()
        }
    }
}