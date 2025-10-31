package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.ItemLevelBinding

class ListLevelAdapter(
    private val level: List<Level>,
    private val selectLevel: (String) -> Unit
) : RecyclerView.Adapter<ListLevelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLevelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_level, parent, false)
        val binding = ItemLevelBinding.bind(view)
        return ListLevelViewHolder(binding)
    }

    override fun getItemCount(): Int = level.size

    override fun onBindViewHolder(holder: ListLevelViewHolder, position: Int) {
        val item = level[position]
        holder.bind(item, selectLevel)
    }
}