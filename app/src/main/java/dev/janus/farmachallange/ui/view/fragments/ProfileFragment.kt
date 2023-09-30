package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentProfileBinding
import dev.janus.farmachallange.utils.UserManager

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()  = _binding!!

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
    }
    fun setUpInfoUser(){
        binding.tvName.text = UserManager.getInstanceUser().nombre
        binding.tvEmail.text = UserManager.getInstanceUser().email
        binding.tvMatricula.text = "Matricula: ${UserManager.getInstanceUser().matricula}"
    }
}