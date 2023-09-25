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
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarazulF.png?alt=media&token=44e5775a-7c66-4f00-9079-e18777a9b9d6",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarnaranjaM.png?alt=media&token=1fc55420-db3e-443e-b6ac-795926e06196",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarmoradoF.png?alt=media&token=e2a10e96-3205-47a2-9241-a914361423a3",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarrosaF.png?alt=media&token=b01a9961-58a5-476d-9718-6da881e805db",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarverdeM.png?alt=media&token=2f2d33b3-0369-4027-a31b-ff67ae82066a",
        "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarrojoM.png?alt=media&token=47b79537-1ac5-42f8-90b5-0844c80d51f1"
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