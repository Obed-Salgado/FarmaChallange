package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.databinding.FragmentProfileBinding
import dev.janus.farmachallange.ui.view.adapters.AchievementAdapter
import dev.janus.farmachallange.ui.viewmodel.MenuViewModel
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.provideList

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()  = _binding!!
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInfoUser()
        onBackPressed()

//        viewModel.nameLevel.observe(viewLifecycleOwner){
//            setUpRecyclerView(it)
//        }

        setUpRecyclerView(provideList())
    }

    private fun setUpInfoUser(){
        binding.tvName.text = UserManager.getInstanceUser().nombre
        binding.tvEmail.text = UserManager.getInstanceUser().email
        binding.tvMatricula.text = UserManager.getInstanceUser().matricula
    }

    private fun setUpRecyclerView(achievement: List<Nivel>){
        binding.rvAchievement.adapter = AchievementAdapter(achievement) { }
        binding.rvAchievement.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }
}