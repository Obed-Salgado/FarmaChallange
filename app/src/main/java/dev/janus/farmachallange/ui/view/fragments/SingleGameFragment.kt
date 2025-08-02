package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentSingleGameBinding
import dev.janus.farmachallange.ui.view.dialog.EvaluationDialog
import dev.janus.farmachallange.ui.view.dialog.ResultadosDialog
import dev.janus.farmachallange.ui.viewmodel.SingleGameViewModel
import dev.janus.farmachallange.utils.Constants.COST_HELP_COIN
import dev.janus.farmachallange.utils.Constants.COST_REWARD_COIN
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.Timer


@AndroidEntryPoint
class SingleGameFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = Timer(20000)
        idNivel = args.idNivel
        idRonda = args.idRonda

        onBackPressed()
        setUpButtonList()
        setObservers()
        setListeners()
        viewModel.getAllQuestions(idNivel, idRonda)
    }

    private fun overCoins() {
        Toast.makeText(requireContext(), "Te has quedado sin monedas", Toast.LENGTH_SHORT).show()
    }

    private fun insuficientCoins() {
        Toast.makeText(requireContext(), "No tienes monedas suficientes", Toast.LENGTH_SHORT).show()
    }

    private fun nonDistractor() {
        Toast.makeText(requireContext(), "Sólo puedes usar una ayuda por pregunta", Toast.LENGTH_SHORT).show()
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
            binding.tvTiempo.text = "$secondsRemaining"
        }
    }

    private fun onFinish() {
        if (UserManager.getInstanceUser().corazones != 0) {
            incorrectAnswer++
            showDialog("overtime")
            viewModel.updateHearts(UserManager.getInstanceUser().corazones - 1)
        } else
            overHerts()
    }

    private fun showTimer() {
        binding.prbTiempo.progress = 0
        timer.cancelTem()
        timer.startTemp(onTick = { onTick(it) }, onFinish = { onFinish() })

    }

    private fun setUpButtonList() {
        buttonList = listOf(
            binding.btnChoice1, binding.btnChoice2, binding.btnChoice3, binding.btnChoice4
        )
    }

    private fun evaluateQuestion(buttonRes: Button) {
        if (UserManager.getInstanceUser().corazones != 0) {
            timer.cancelTem()
            if (buttonRes.text == respuestaOk) {
                correctAnswer++
                buttonRes.background = AppCompatResources.getDrawable(requireContext(), R.drawable.background_button_corect)
                showDialog("correct")
                viewModel.updateCoins(UserManager.getInstanceUser().monedas + COST_REWARD_COIN)
            } else {
                incorrectAnswer++
                buttonRes.background = AppCompatResources.getDrawable(requireContext(), R.drawable.background_button_incorect)
                showDialog("incorrecto")
                //Agregar respuesta incorrecta para estadistica
//                val nivel = idNivel.removeRange(0, idNivel.length - 1)
//                viewModel.setWrongAnswer(nivel.toInt(), idRonda.toInt(), pregunta, butonRes.text.toString(), binding.tvPregunta.text.toString())
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

    private fun goToHome() = findNavController().popBackStack(R.id.menuFragment, false)

    private fun setUpQuestion() {
        viewModel.getQuestion(pregunta)
        distractorClicks = 0
        pregunta++
    }

    private fun showResultGame() {
        binding.prbTiempo.progress = 0
        binding.tvTiempo.text = ""
        timer.cancelTem()
        val dialog = ResultadosDialog(correctAnswer, incorrectAnswer) { goToHome() }
        dialog.show(childFragmentManager, "ResultadosDialog")
    }

    private fun showDialog(nameDialog: String) {
        val dialog = EvaluationDialog(
            description, { setUpQuestion() }, nameDialog
        )
        dialog.show(childFragmentManager, "CorrectDialog")
    }

    private fun setUpOptions(respuesta: String, distractores: List<String>) {
        val options = distractores.toMutableList()
        options.add(respuesta)
        options.shuffle()
        for (i in buttonList.indices) {
            val button = buttonList[i]
            button.isVisible = true
            button.background = AppCompatResources.getDrawable(requireContext(), R.drawable.background_button_choice)
            button.text = options[i]
        }
    }

    private fun setObservers(){
        viewModel.question.observe(viewLifecycleOwner) { pregunta ->
            try {
                showTimer()
                binding.tvPregunta.text = pregunta.pregunta
                setUpOptions(pregunta.respuesta, pregunta.distractores)
                description = pregunta.descripcion
                respuestaOk = pregunta.respuesta
            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${ex.message} \n ${pregunta.distractores.size}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.finishGame.observe(viewLifecycleOwner){
            showResultGame()
        }

        viewModel.numberOfQuestions.observe(viewLifecycleOwner){
            setUpQuestion()
        }

        viewModel.numberquest.observe(viewLifecycleOwner) {
            binding.tvCountQuest.text = it
        }
    }

    private fun setListeners(){
        for (i in buttonList.indices) {
            buttonList[i].setOnClickListener {
                evaluateQuestion(buttonList[i])
            }
        }

        binding.btnHelp.setOnClickListener { //CHECAR como es la lógica de quitar distractores
            if (UserManager.getInstanceUser().monedas >= COST_HELP_COIN && distractorClicks < 1) {
                viewModel.updateCoins(UserManager.getInstanceUser().monedas - COST_HELP_COIN)
                deleteDistractor()
            } else if(UserManager.getInstanceUser().monedas == 0)
                overCoins()
            else if(UserManager.getInstanceUser().monedas < COST_HELP_COIN)
                insuficientCoins()
            else
                nonDistractor()
        }
    }

    //Este metodo sirve para que el usuario no pueda regresar al fragmento anterior por medio de la navegacion del dispositivo.
    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }
}