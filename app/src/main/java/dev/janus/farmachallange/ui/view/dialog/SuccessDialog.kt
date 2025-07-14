package dev.janus.farmachallange.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.DialogFragment
import dev.janus.farmachallange.databinding.DialogSuccessBinding

class SuccessDialog(private val message: String, private val onClose: () -> Unit): DialogFragment() {

    private lateinit var binding: DialogSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMessage.text = message
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        binding.btnClose.setOnClickListener {
            onClose()
            dismiss()
        }
    }
}