package dev.janus.farmachallange.ui.view.fragments.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
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
                binding.tvFailure.text = "Se requiere ingresar un correo y una corntraseña"
                binding.tvFailure.isVisible = true
            }else{
                showShimmer()
                viewModel.setUserData(binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    onSuccess = {
                        val intent = Intent(getActivity(), GameActivity::class.java)
                        startActivity(intent)
                        hidenShimmer()
                        requireActivity().finish()

                    },
                    onFailure = { errorMessage ->
                        binding.tvFailure.text = errorMessage
                        binding.tvFailure.isVisible = true
                        hidenShimmer()
                    })
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun showShimmer() {
        binding.viewLoading.isVisible = true
        binding.constraintData.isVisible = false
        binding.btnRegister.isVisible = false
        binding.btnLoguear.isVisible = false
    }

    private fun hidenShimmer() {
        binding.viewLoading.isVisible = false
        binding.constraintData.isVisible = true
        binding.btnRegister.isVisible = true
        binding.btnLoguear.isVisible = true
    }
}