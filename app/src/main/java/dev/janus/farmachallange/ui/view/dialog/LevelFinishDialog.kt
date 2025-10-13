package dev.janus.farmachallange.ui.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import dev.janus.farmachallange.databinding.DialogFinishLevelBinding

class LevelFinishDialog: DialogFragment() {

    private lateinit var binding: DialogFinishLevelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFinishLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/levelsIcons%2Fconceptos_generales.png?alt=media&token=c0bd073f-d970-4ace-bbdb-b7d170439702")
            .into(binding.ivSticker)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}