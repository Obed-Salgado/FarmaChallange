package dev.janus.farmachallange.ui.view.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentSingleGameBinding
import dev.janus.farmachallange.ui.view.dialog.EvaluationDialog
import dev.janus.farmachallange.ui.view.dialog.ResultadosDialog
import dev.janus.farmachallange.ui.viewmodel.SingleGameViewModel
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.Timer


@AndroidEntryPoint
class SingleGameFragment() : Fragment() {
    private var _binding: FragmentSingleGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SingleGameViewModel by viewModels()
    private lateinit var buttonList: List<Button>
    private lateinit var description: String
    private lateinit var respuestaOk: String
    private val args: SingleGameFragmentArgs by navArgs()
    private lateinit var idNivel: String
    private lateinit var idRonda: String
    private var pregunta: Int = 0
    private lateinit var timer: Timer
    private var distractorClicks: Int = 0
    private  var correctAnswer:Int = 0
    private  var incorrectAnswer:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()

        timer = Timer(20000)
        listaBotones()
        timer.startTemp(onTick = { onTick(it) }, onFinish = { onFinish() })
        idNivel = args.idNivel
        idRonda = args.idRonda
        viewModel.numberquest.observe(viewLifecycleOwner, Observer {
            binding.tvCountQuest.text = it
        })
        actualizarInterfaz()
        binding.buttonPreg.setOnClickListener {
            if (UserManager.getInstanceUser().monedas > 0) {
                viewModel.updateCoins(UserManager.getInstanceUser().monedas - 5)
                generarPregunta()
            } else
                overCoins()
        }

        for (i in buttonList.indices) {
            buttonList[i].setOnClickListener {
                evaluarPregunta(buttonList[i])
            }
        }

        binding.btnHelp.setOnClickListener {
            if (UserManager.getInstanceUser().monedas != 0 && distractorClicks < 2) {
                viewModel.updateCoins(UserManager.getInstanceUser().monedas - 5)
                deleteDistractor()
            } else
                overCoins()
        }
    }

    //Este metodo sirve para que el usuario no pueda regresar al fragmento anterior por medio de la navegacion del dispositivo.
    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }


    private fun overCoins() {
        Toast.makeText(requireContext(), "Te has quedado sin monedas", Toast.LENGTH_SHORT).show()
    }

    private fun overHerts() {
        Toast.makeText(requireContext(), "Te has quedado sin corazones", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancelTem()
    }

    private fun onTick(secondsRemaining: Long) {
        if (UserManager.getInstanceUser().corazones != 0) {
            binding.prbTiempo.progress += 5
            binding.tvTiempo.text = secondsRemaining.toString()
        }
    }

    private fun onFinish() {
        if (UserManager.getInstanceUser().corazones != 0) {
            showDialog("overtime")
            viewModel.updateHearts(UserManager.getInstanceUser().corazones - 1)
        } else
            overHerts()
    }

    private fun reiniciarTemp() {
        binding.prbTiempo.progress = 0
        timer.cancelTem()
        timer.startTemp(onTick = { onTick(it) }, onFinish = { onFinish() })
    }

    private fun listaBotones() {
        buttonList = listOf(
            binding.btnChoice1, binding.btnChoice2, binding.btnChoice3, binding.btnChoice4
        )
    }

    private fun generarPregunta() {
        reiniciarTemp()
        actualizarInterfaz()
        restablecerBotones()
    }

    fun evaluarPregunta(butonRes: Button) {

        if (UserManager.getInstanceUser().corazones != 0) {
            timer.cancelTem()
            if (butonRes.text == respuestaOk) {
                //Respuesta correcta
                correctAnswer++
                butonRes.background =
                    requireContext().getDrawable(R.drawable.background_button_corect)
                showDialog("correct")
                viewModel.updateCoins(UserManager.getInstanceUser().monedas + 5)
            } else {
                //Respuesta Correcta
                incorrectAnswer++
                showDialog("incorrecto")
                butonRes.background =
                    requireContext().getDrawable(R.drawable.background_button_incorect)
                buscarRespuesta()
                val nivel = idNivel.removeRange(0, idNivel.length - 1)
                viewModel.setWrongAnswer(nivel.toInt(), idRonda.toInt(), pregunta, butonRes.text.toString(), binding.tvPregunta.text.toString())
                viewModel.updateHearts(UserManager.getInstanceUser().corazones - 1)
            }
        } else overHerts()
    }


    private fun deleteDistractor() {
        distractorClicks++
        val distractorButtons = buttonList.filter { it.text != respuestaOk }
        if (distractorButtons.isNotEmpty()) {
            val randomDistractor = distractorButtons.random()
            randomDistractor.isVisible = false // Oculta el botón distractor
        }


    }


    private fun buscarRespuesta() {
        for (i in buttonList.indices) {
            if (buttonList[i].text == respuestaOk) {
                buttonList[i].background =
                    requireContext().getDrawable(R.drawable.background_button_corect)
                break
            }
        }
    }

    private fun showOverRonda() {
        timer.cancelTem()
        val dialog = ResultadosDialog(correctAnswer, incorrectAnswer){goToHome()}
        dialog.show(childFragmentManager, "ResultadosDialog")
    }

    private fun goToHome() = findNavController().navigate(R.id.action_singleGameFragment_to_menuFragment)



    private fun actualizarInterfaz() {
        pregunta++
        viewModel.fetchQuestions(idNivel, idRonda, pregunta) { showOverRonda() }
        viewModel.pregunta.observe(viewLifecycleOwner, Observer { pregunta ->
            try {
                binding.tvPregunta.text = pregunta.pregunta
                val opciones = mutableListOf<String>()
                opciones.addAll(pregunta.distractores)
                opciones.add(pregunta.respuesta)
                opciones.shuffle()
                for (i in opciones.indices) {
                    buttonList[i].text = opciones[i]
                }
                description = pregunta.descripcion
                respuestaOk = pregunta.respuesta
            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${ex.message} \n ${pregunta.distractores.size}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    private fun showDialog(nameDialog: String) {
        val dialog = EvaluationDialog(
            description, { generarPregunta() }, nameDialog
        )
        dialog.show(childFragmentManager, "CorrectDialog")
    }

    private fun restablecerBotones() {
        for (i in buttonList.indices) {
            buttonList[i].isVisible = true
            buttonList[i].background =
                requireContext().getDrawable(R.drawable.background_button_choice)
        }
    }
}