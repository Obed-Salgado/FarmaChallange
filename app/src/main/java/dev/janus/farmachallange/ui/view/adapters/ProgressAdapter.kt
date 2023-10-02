package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.ItemProgressBinding

class ProgressAdapter(): RecyclerView.Adapter<ProgressViewHolder>() {

    private val etapas = listOf(1, 2, 3, 4, 5, 6)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_progress, parent, false)
        val binding = ItemProgressBinding.bind(view)
        return ProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val item = etapas[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = etapas.size
}