package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.databinding.ItemRondasBinding

class RondaListAdapter(private val ronda: List<Ronda>, private val clickItem:(String)->Unit):RecyclerView.Adapter<RondaListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RondaListViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_rondas,parent, false)
        val binding = ItemRondasBinding.bind(view)
        return RondaListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RondaListViewHolder, position: Int) {
        val item = ronda[position]
        holder.bind(item,clickItem)
    }

    override fun getItemCount(): Int = ronda.size
}