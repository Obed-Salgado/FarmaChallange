package dev.janus.farmachallange.ui.view.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.databinding.ItemLevelBinding

class ListLevelViewHolder(private val binding:ItemLevelBinding): RecyclerView.ViewHolder(binding.root) {
fun bind(resul:Nivel, selectNivel:(String)->Unit){
    binding.tvNameLevel.text = resul.nombre
    binding.cvLevel.setCardBackgroundColor(Color.TRANSPARENT)
    itemView.setOnClickListener{
        selectNivel(resul.id)
    }
}

}