package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.databinding.FragmentMenuBinding
import dev.janus.farmachallange.ui.view.adapters.ListLevelAdapter
import dev.janus.farmachallange.ui.viewmodel.MenuViewModel

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inintRecyclerView()
        onBackPressed()

        menuViewModel.showLottie.observe(viewLifecycleOwner){
            showLottie(it)
        }
    }

    private fun inintRecyclerView(){
        menuViewModel.nameLevel.observe(viewLifecycleOwner) { levels ->
            binding.rvLevel.adapter = ListLevelAdapter(levels, selectLevel = { changeFragment(it) })
            binding.rvLevel.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun changeFragment(idNivel:String){
        findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToRondasFragment(idNivel))
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun showLottie(show: Boolean) {
        binding.viewLoading.isVisible = show
    }
}