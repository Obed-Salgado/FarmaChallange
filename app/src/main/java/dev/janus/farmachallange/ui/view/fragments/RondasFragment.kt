package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.databinding.FragmentRondasBinding
import dev.janus.farmachallange.ui.view.adapters.RondaListAdapter
import dev.janus.farmachallange.ui.viewmodel.RondasViewModel
import dev.janus.farmachallange.utils.UserManager

@AndroidEntryPoint
class RondasFragment : Fragment() {
    private var _biniding:FragmentRondasBinding?=null
    private val binding get() = _biniding!!
    private val rondasViewModel:RondasViewModel by viewModels()
    private lateinit var adapter:RondaListAdapter
    val args:RondasFragmentArgs by navArgs()
    private lateinit var idNivel:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _biniding = FragmentRondasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idNivel = args.idRonda
        rondasViewModel.getRonda(idNivel)
        initObservers()
    }

    private fun initObservers(){
        rondasViewModel.nameRonda.observe(viewLifecycleOwner) { nameRonda ->
            adapter = RondaListAdapter(nameRonda, clickItem = { goToGameFragment(it) })
            binding.rvRondas.adapter = adapter
            binding.rvRondas.layoutManager = LinearLayoutManager(requireContext())
        }

        rondasViewModel.showLottie.observe(viewLifecycleOwner){
            showLottie(it)
        }
    }

    private fun goToGameFragment(idRonda:String){
        if (UserManager.getInstanceUser().corazones != 0){
            findNavController().navigate(RondasFragmentDirections.actionRondasFragmentToSingleGameFragment(idNivel,idRonda))
        }
        else{
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Sin corazones")
            builder.setMessage("No quedan mas corazones, espera a que recarguen para poder jugar")
            // Agregar un botón de "Aceptar"
            builder.setPositiveButton("Aceptar") { dialog, which ->
                // Acción a realizar al hacer clic en "Aceptar"
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun showLottie(show: Boolean) {
        binding.viewLoading.isVisible = show
    }
}



