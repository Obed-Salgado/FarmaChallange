package dev.janus.farmachallange.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.databinding.ItemRondasBinding

class RondaListViewHolder(private val binding:ItemRondasBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(result: Ronda, clicItem:(String)->Unit){
        binding.btnRondaSelect.text = result.nombre
        binding.btnRondaSelect.setOnClickListener {
            clicItem(result.id)
        }
    }

}