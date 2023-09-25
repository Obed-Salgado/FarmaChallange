package dev.janus.farmachallange.ui.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.DialogEvaluationBinding

class EvaluationDialog(
    private val respuesta: String,
    private val generarPregunta: () -> Unit,
    private val respuestaOk: String
) : DialogFragment() {

    private lateinit var binding: DialogEvaluationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEvaluationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDescription.text = respuesta
        if (respuestaOk == "incorrecto"){
            errorAnswer()
        }else if (respuestaOk == "overtime"){
            overTimeAnswer()
        }
        setUpEvents()
        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setUpEvents() {
        binding.btnNext.setOnClickListener {
            dismiss()
            generarPregunta()
        }
    }

    private fun errorAnswer() {
        binding.tvOK.text = "¡INCORRECTO!"
        binding.ivTop.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_dialog_incorrect)
        binding.btnNext.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_button_incorect)
        hiddenCoin()
    }

    private fun overTimeAnswer(){
        binding.tvOK.text = "¡SE AGOTO EL TIEMPO!"
        binding.ivTop.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_dialog_overtime)
        binding.btnNext.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_button_overtime)
        hiddenCoin()
    }

    private fun hiddenCoin(){
        binding.ivCoin.isVisible = false
        binding.tvNumCoin.isVisible = false
    }
}