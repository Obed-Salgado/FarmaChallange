package dev.janus.farmachallange.ui.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.janus.farmachallange.databinding.DialogOverRoundBinding

class ResultadosDialog(val correct:Int, val incorrect:Int, val onClick:()->Unit): DialogFragment() {
    private lateinit var binding: DialogOverRoundBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOverRoundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.tvShowCorrect.text = correct.toString()
        binding.tvShowIncorrect.text = incorrect.toString()
        binding.btnContinuar.setOnClickListener {onClick()}
    }
}