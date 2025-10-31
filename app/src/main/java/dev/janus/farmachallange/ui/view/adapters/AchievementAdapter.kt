package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.ItemAchievementBinding

class AchievementAdapter(
    private val achievement: List<Level>,
    private val selectAchievement: (String) -> Unit
): RecyclerView.Adapter<AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_achievement, parent, false)
        val binding = ItemAchievementBinding.bind(view)
        return AchievementViewHolder(binding)
    }

    override fun getItemCount(): Int  = achievement.size

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val item = achievement[position]
        holder.render(item, selectAchievement)
    }
}