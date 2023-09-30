package dev.janus.farmachallange.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.databinding.ActivityGameBinding
import dev.janus.farmachallange.ui.viewmodel.GameActivityViewModel
import dev.janus.farmachallange.utils.UserManager

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {
    private var timeRemainingMillis = 10000
    private lateinit var binding: ActivityGameBinding
    private val viewModel: GameActivityViewModel by viewModels()
    private var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  ocultarButtonNav()
        // Configurar el NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmenttMenu) as NavHostFragment
        val navController = navHostFragment.navController

        // Agregar un listener para detectar cambios en el destino del NavController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Verificar si el fragmento actual debe ocultar el BottomNavigationView
            if (destination.id == R.id.singleGameFragment) {
                binding.navigationView.visibility = View.GONE
            } else {
                binding.navigationView.visibility = View.VISIBLE
            }
        }

        binding.navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicio -> binding.navHostFragmenttMenu.findNavController().navigate(R.id.menuFragment)
                R.id.perfil -> binding.navHostFragmenttMenu.findNavController().navigate(R.id.profileFragment)

            }

            true
        }

        observeUserData()
    }



    private fun observeUserData() {
        viewModel.fetchUser.observeForever { user ->
            updateUserInfo(user)
            /* if (binding.tvCorazon.text.toString().toInt() < 12) {
                 binding.tvTime.isVisible = true
                 startTimer()
             }*/

        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeRemainingMillis.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                // Actualizar el tiempo restante en el TextView
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.tvTime.text = "${String.format("%02d:%02d", minutes, seconds)}"
            }

            override fun onFinish() {
                if (UserManager.getInstanceUser().corazones < 12) {
                    viewModel.updateHeats(UserManager.getInstanceUser().corazones + 1)
                    // Reiniciar el temporizador
                    timeRemainingMillis = 10000 // 2 minutos en milisegundos
                    startTimer()
                } else
                    binding.tvTime.isVisible = false
            }
        }.start()
    }

    private fun updateUserInfo(user: Usuario?) {
        if (user != null) {
            binding.tvName.text = user.usuario
            binding.tvCorazon.text = user.corazones.toString()
            binding.tvMoneda.text = user.monedas.toString()
            Glide.with(this)
                .load(user.urlIcon)
                .into(binding.ivIconUser)
        } else {
            Toast.makeText(this, "No hay datos de usuario", Toast.LENGTH_SHORT).show()
        }

    }

}