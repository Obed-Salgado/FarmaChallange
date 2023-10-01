package dev.janus.farmachallange.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.ItemIconUserBinding

class IconUserAdapter(
    private val selectIcon: (String) -> Unit,
    private val dismiss: () -> Unit
): RecyclerView.Adapter<IconUserViewHolder>() {

    private var iconUrls: List<String> = listOf(
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarazulF.png?alt=media&token=b7e1fac4-b929-4a15-8c16-098ebaf492b4",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarnaranjaM.png?alt=media&token=e9b26629-7a94-4a8b-b72b-f3c07b6148e2",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarmoradoF.png?alt=media&token=86541bc4-8b43-4664-bd16-4ff4120102db",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarrosaF.png?alt=media&token=93b69698-5b8e-4bdb-b422-8c39d66a766d",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarverdeM.png?alt=media&token=0dfa1ade-0f29-4706-8b0c-388a44b6fd06",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/icon%2FavatarrojoM.png?alt=media&token=ac84281e-b96c-46f2-a68f-51fbe849f7e6"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconUserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_icon_user, parent, false)
        val binding = ItemIconUserBinding.bind(view)
        return IconUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconUserViewHolder, position: Int) {
        val url = iconUrls[position]
        holder.render(url, selectIcon, dismiss)
    }

    override fun getItemCount(): Int = iconUrls.size
}