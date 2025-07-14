package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.databinding.ItemProgressBinding

class ProgressAdapter(private val progress: List<Progress>): RecyclerView.Adapter<ProgressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_progress, parent, false)
        val binding = ItemProgressBinding.bind(view)
        return ProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val item = progress[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = progress.size
}