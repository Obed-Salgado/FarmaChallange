package dev.janus.farmachallange.ui.view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.janus.farmachallange.databinding.ItemIconUserBinding

class IconUserViewHolder(private val binding: ItemIconUserBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(url: String, selectIcon: (String) -> Unit, dismiss: () -> Unit){
        Glide.with(binding.root.context)
            .load(url)
            .into(binding.ivAzul)

        this.itemView.setOnClickListener {
            selectIcon(url)
            dismiss()
        }
    }
}