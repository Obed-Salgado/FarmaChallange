package dev.janus.farmachallange.ui.view.fragments.login

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.databinding.FragmentRegisterBinding
import dev.janus.farmachallange.ui.view.dialog.UserIconDialog
import dev.janus.farmachallange.ui.viewmodel.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
    private var urlIcon = "https://firebasestorage.googleapis.com/v0/b/farmachallange1.appspot.com/o/avatarnaranjaM.png?alt=media&token=1fc55420-db3e-443e-b6ac-795926e06196"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            showShimmer()
            viewModel.setUserData(
                binding.etNombre.text.toString(),
                binding.etUser.text.toString(),
                binding.etMatricula.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                urlIcon,
                onSuccess = {
                    Toast.makeText(requireContext(), "Usuario Guardado", Toast.LENGTH_SHORT).show()
                    hidenShimmer()
                },
                onFailure = {errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            )
            limpiarTexto()
        }

        binding.ivIcon.setOnClickListener {
            val dialogUserIconBinding = UserIconDialog( selectIcon ={ url ->
                selectIcon(url)
            } )
            dialogUserIconBinding.show(parentFragmentManager, "UserIconDialog")
        }
    }

    private fun selectIcon(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(binding.ivIcon)
        urlIcon = url
    }

    private fun showShimmer(){
        binding.viewLoading.isVisible = true
        binding.constraintRegister.isVisible = false
        binding.btnRegister.isVisible = false
    }
    private fun hidenShimmer(){
        binding.viewLoading.isVisible = false
        binding.constraintRegister.isVisible = true
        binding.btnRegister.isVisible = true
    }

    private fun limpiarTexto() {
        binding.etNombre.setText("")
        binding.etUser.setText("")
        binding.etMatricula.setText("")
        binding.etEmail.setText("")
        binding.etPassword.setText("")
        binding.ivIcon.setImageResource(R.drawable.userlog)
    }
}