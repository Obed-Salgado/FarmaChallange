package dev.janus.farmachallange.ui.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.InputsError
import dev.janus.farmachallange.data.model.UserRegister
import dev.janus.farmachallange.databinding.FragmentRegisterBinding
import dev.janus.farmachallange.ui.view.dialog.RegisterResponseDialog
import dev.janus.farmachallange.ui.view.dialog.RegisterResponseDialog.StateResponse
import dev.janus.farmachallange.ui.view.dialog.UserIconDialog
import dev.janus.farmachallange.ui.viewmodel.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
    private var urlIcon = "" //https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarnaranjaM.png?alt=media&token=1fc55420-db3e-443e-b6ac-795926e06196

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnRegister.setOnClickListener {
            val user = UserRegister(
                binding.etNombre.text.toString(),
                binding.etUser.text.toString(),
                binding.etMatricula.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                urlIcon,
            )
            viewModel.setUserData(user)
        }

        binding.ivIcon.setOnClickListener {
            val dialogUserIconBinding = UserIconDialog { url -> selectIcon(url) }
            dialogUserIconBinding.show(parentFragmentManager, "UserIconDialog")
        }

        viewModel.showLottie.observe(viewLifecycleOwner){
            showShimmer(it)
        }

        viewModel.successMessage.observe(viewLifecycleOwner){
            RegisterResponseDialog(StateResponse.SUCCESS){
                binding.btnBack.performClick()
            }.show(parentFragmentManager, "RegisterResponseDialog")
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            RegisterResponseDialog(StateResponse.ERROR){
                binding.btnBack.performClick()
            }.show(parentFragmentManager, "RegisterResponseDialog")
        }

        viewModel.errorInputs.observe(viewLifecycleOwner){
            showErrorText(it)
        }
    }

    private fun selectIcon(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(binding.ivIcon)
        urlIcon = url
    }

    private fun showShimmer(show: Boolean){
        binding.viewLoading.isVisible = show
        binding.constraintRegister.isVisible = !show
        binding.btnRegister.isVisible = !show
    }

    private fun showErrorText(error: InputsError){
        binding.tvNameError.text = if(error.nameError) "Ingresa un nombre válido" else ""
        binding.tvUserNameError.text = if(error.userNameError) "Ingresa un usuario válido" else ""
        binding.tvTuitionError.text = if(error.tuitionError) "Ingresa una matrícula válida" else ""
        binding.tvEmailError.text = if(error.emailError) "Ingresa un correo válido" else ""
        binding.tvPasswordError.text = if(error.passwordError) "Ingresa un contraseña válida" else ""
        binding.tvIconError.text = if(error.urlIconError) "Selecciona un avatar" else ""
    }

    private fun clearInputs() {
        binding.etNombre.setText("")
        binding.etUser.setText("")
        binding.etMatricula.setText("")
        binding.etEmail.setText("")
        binding.etPassword.setText("")
        urlIcon = ""
        binding.ivIcon.setImageResource(R.drawable.userlog)
    }
}