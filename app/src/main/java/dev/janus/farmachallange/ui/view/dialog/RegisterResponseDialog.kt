package dev.janus.farmachallange.ui.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.janus.farmachallange.R
import android.graphics.Color
import dev.janus.farmachallange.databinding.DialogRegisterResponseBinding

class RegisterResponseDialog(private val response: StateResponse, private val onClose: () -> Unit): DialogFragment() {

    private lateinit var binding: DialogRegisterResponseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRegisterResponseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(response){
            StateResponse.SUCCESS -> setUpInfo("Registro exitoso", R.drawable.ic_success)
            StateResponse.ERROR -> setUpInfo("Error al registrar", R.drawable.ic_failure)
        }

        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding.btnClose.setOnClickListener {
            onClose()
            dismiss()
        }
    }

    private fun setUpInfo(message: String, image: Int){
        binding.tvMessage.text = message
        binding.ivIconResponse.setImageResource(image)
    }

    enum class StateResponse{
        SUCCESS,
        ERROR
    }
}