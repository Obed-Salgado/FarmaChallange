package dev.janus.farmachallange.ui.view.fragments.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentLoginBinding
import dev.janus.farmachallange.ui.view.GameActivity
import dev.janus.farmachallange.ui.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoguear.setOnClickListener {
            if (TextUtils.isEmpty(binding.etEmail.getText()) || TextUtils.isEmpty(binding.etPassword.getText())) {
                binding.tvFailure.text = getString(R.string.requires_email_and_password)
                binding.tvFailure.isVisible = true
            }else
                viewModel.setUserData(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.showLottie.observe(viewLifecycleOwner){
            showLottie(it)
        }

        viewModel.successMessage.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(), GameActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            binding.tvFailure.text = it
            binding.tvFailure.isVisible = true
        }
    }

    private fun showLottie(show: Boolean) {
        binding.viewLoading.isVisible = show
        binding.constraintData.isVisible = !show
        binding.btnRegister.isVisible = !show
        binding.btnLoguear.isVisible = !show
    }
}