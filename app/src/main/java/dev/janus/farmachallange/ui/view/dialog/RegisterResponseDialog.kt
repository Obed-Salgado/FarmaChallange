package dev.janus.farmachallange.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import dev.janus.farmachallange.R
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

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        binding.btnClose.setOnClickListener {
            onClose()
            dismiss()
        }
        onBackPressed()
    }

    private fun setUpInfo(message: String, image: Int){
        binding.tvMessage.text = message
        binding.ivIconResponse.setImageResource(image)
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }

    enum class StateResponse{
        SUCCESS,
        ERROR
    }
}