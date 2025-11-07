package dev.janus.farmachallange.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.databinding.FragmentProfileBinding
import dev.janus.farmachallange.ui.view.MainActivity
import dev.janus.farmachallange.ui.view.adapters.AchievementAdapter
import dev.janus.farmachallange.ui.viewmodel.LoginViewModel
import dev.janus.farmachallange.ui.viewmodel.MenuViewModel
import dev.janus.farmachallange.utils.Constants.SHARED_LEVELS_KEY
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.UniformItemDecoration
import dev.janus.farmachallange.utils.dpToPx
import dev.janus.farmachallange.utils.provideList

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()  = _binding!!
    private val viewModel: MenuViewModel by viewModels()
    private val loginVM: LoginViewModel by viewModels()

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

        val levels = arguments?.getParcelableArrayList<Level>(SHARED_LEVELS_KEY)

//        viewModel.nameLevel.observe(viewLifecycleOwner){
//            setUpRecyclerView(it)
//        }

        setUpRecyclerView(provideList())

        binding.btnLogOut.setOnClickListener{
            loginVM.closeSession()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setUpInfoUser(){
        with(UserManager.getInstanceUser()){
            binding.tvName.text = nombre
            binding.tvEmail.text = email
            binding.tvMatricula.text = matricula
        }
    }

    private fun setUpRecyclerView(achievement: List<Level>){
        val uniformPaddingInPx = 4.dpToPx(requireContext())
        val itemDecoration = UniformItemDecoration(uniformPaddingInPx)
        binding.rvAchievement.addItemDecoration(itemDecoration)
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