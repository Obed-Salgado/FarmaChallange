package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentSingleGameBinding
import dev.janus.farmachallange.ui.view.dialog.EvaluationDialog
import dev.janus.farmachallange.ui.viewmodel.SingleGameViewModel
import dev.janus.farmachallange.utils.UserManager

@AndroidEntryPoint
class SingleGameFragment : Fragment() {
    private var _binding: FragmentSingleGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SingleGameViewModel by viewModels()

    private lateinit var buttonList: List<Button>
    private lateinit var description: String
    private lateinit var respuestaOk: String
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaBotones()
        startTemp()
        actualizarInterfaz()

        binding.buttonPreg.setOnClickListener {
            if (UserManager.getInstanceUser().monedas != 0){
                viewModel.updateCoins(UserManager.getInstanceUser().monedas-5)
                generarPregunta()
            }
            else
                overCoins()
        }

        for (i in buttonList.indices) {
            buttonList[i].setOnClickListener {
                evaluarPregunta(buttonList[i])
            }
        }

        binding.btnHelp.setOnClickListener {
            if (UserManager.getInstanceUser().monedas != 0){
                viewModel.updateCoins(UserManager.getInstanceUser().monedas-5)
                deleteDistractor()
            }
            else
                overCoins()
        }
    }

    private fun overCoins(){
        Toast.makeText(requireContext(), "Te has quedado sin monedas",Toast.LENGTH_SHORT).show()
    }

    private fun overHerts(){
        Toast.makeText(requireContext(), "Te has quedado sin corazones",Toast.LENGTH_SHORT).show()
    }


    private fun startTemp() {
        if (UserManager.getInstanceUser().corazones!=0){
            timer = object : CountDownTimer(20000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.prbTiempo.progress += 5
                    binding.tvTiempo.text = secondsRemaining.toString()

                }

                override fun onFinish() {
                    if (UserManager.getInstanceUser().corazones != 0){
                        showDialog("overtime")
                        viewModel.updateHeats(UserManager.getInstanceUser().corazones - 1)
                    }
                    else
                        overHerts()

                }
            }
            timer.start()
        }else
            overHerts()

    }

    private fun reiniciarTemp() {
        binding.prbTiempo.progress = 0
        timer.cancel()
        startTemp()
    }

    private fun listaBotones() {
        buttonList = listOf(
            binding.btnChoice1, binding.btnChoice2, binding.btnChoice3, binding.btnChoice4
        ).shuffled()
    }

    private fun generarPregunta() {
        reiniciarTemp()
        actualizarInterfaz()
        restablecerBotones()
    }

    fun evaluarPregunta(butonRes: Button) {
        //Respuesta correcta
        if (UserManager.getInstanceUser().corazones != 0) {
            timer.cancel()
            if (butonRes.text == respuestaOk) {
                butonRes.background = requireContext().getDrawable(R.drawable.background_button_corect)
                showDialog("correct")
                viewModel.updateCoins(UserManager.getInstanceUser().monedas + 5)
            } else {
                showDialog("incorrecto")
                butonRes.background = requireContext().getDrawable(R.drawable.background_button_incorect)
                buscarRespuesta()

                viewModel.updateHeats(UserManager.getInstanceUser().corazones - 1)
            }
        }
        else overHerts()
    }


    private fun deleteDistractor() {
        val respuestaCorrecta = respuestaOk
        val indicesDistractores = buttonList.indices.filter { buttonIndex ->
            buttonList[buttonIndex].text != respuestaCorrecta
        }
        if (indicesDistractores.isNotEmpty()) {
            val indiceAleatorio = indicesDistractores.random()
            buttonList[indiceAleatorio].isVisible = false // Oculta el botón distractor
        }
    }

    private fun buscarRespuesta() {
        for (i in buttonList.indices) {
            if (buttonList[i].text == respuestaOk) {
                buttonList[i].background = requireContext().getDrawable(R.drawable.background_button_corect)
                break
            }
        }
    }

    private fun actualizarInterfaz() {
        viewModel.fetchQuestions()
        viewModel._pregunta.observe(viewLifecycleOwner, Observer { pregunta ->

            try {
                binding.tvPregunta.text = pregunta.pregunta
                buttonList[0].text = pregunta.distractor[0]
                buttonList[1].text = pregunta.distractor[1]
                buttonList[2].text = pregunta.distractor[2]
                buttonList[3].text = pregunta.respuesta
                description = pregunta.descripcion
                respuestaOk = pregunta.respuesta
            } catch (ex: Exception){
                Toast.makeText(requireContext(), "Error: ${ex.message} \n ${pregunta.distractor.size}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    /*    private fun observeDataPregunta() {
            viewModel.fetchQuestions().observe(viewLifecycleOwner, Observer { pregunta ->
                if (pregunta.isNotEmpty()) {
                    actualizarInterfaz(pregunta.random())
                } else {
                    Toast.makeText(requireContext(), "Lista vacía", Toast.LENGTH_SHORT).show()
                }
            })
        }*/

    private fun showDialog(nameDialog: String) {
        val dialog = EvaluationDialog(
            description, { generarPregunta() }, nameDialog
        )
        dialog.show(childFragmentManager, "CorrectDialog")
    }

    private fun restablecerBotones() {
        for (i in buttonList.indices) {
            buttonList[i].isVisible = true
            buttonList[i].background = requireContext().getDrawable(R.drawable.background_button_choice)
        }
    }
}