package dev.janus.farmachallange.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.core.ActivityScope
import com.google.firebase.inject.Deferred
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.databinding.ActivityGameBinding
import dev.janus.farmachallange.ui.viewmodel.GameActivityViewModel
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.NetworkAvailable
import dev.janus.farmachallange.utils.clases.Timer
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private val viewModel: GameActivityViewModel by viewModels()
    private lateinit var timer: Timer
    private val networkAvailable: NetworkAvailable = NetworkAvailable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (networkAvailable.isNetworkAvailable(this)) {
            timer = Timer(10000)
            //  ocultarButtonNav()
            // Configurar el NavController
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragmenttMenu) as NavHostFragment
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
                    R.id.inicio -> binding.navHostFragmenttMenu.findNavController()
                        .navigate(R.id.menuFragment)

                    R.id.perfil -> binding.navHostFragmenttMenu.findNavController()
                        .navigate(R.id.profileFragment)

                    R.id.progreso -> binding.navHostFragmenttMenu.findNavController()
                        .navigate(R.id.progressFragment)
                }
                true
            }

            observeUserData()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Sin conexion a la red")
            builder.setPositiveButton("Aceptar") { _, _ ->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("No se estableció conexión a internet")
                builder.setPositiveButton("Aceptar") { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Si deseas cerrar la actividad actual
                }
                val alertDialog = builder.create()
                alertDialog.show()

            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    private fun observeUserData() {
        viewModel.fetchUser.observeForever { user ->
            updateUserInfo(user)
            startOrCancelTimer(user.corazones)
        }
    }

    private fun startOrCancelTimer(corazones: Int) {
        val isTimerVisible = corazones < 12
        binding.tvTime.isVisible = isTimerVisible
        if (isTimerVisible) {
            timer.startTempHearts(
                onTick = { minutesRemaining, secondsRemaining ->
                    updateTimerDisplay(minutesRemaining, secondsRemaining)
                },
                onFinish = {
                    handleTimerFinish(corazones)
                }
            )
        } else {
            binding.tvTime.isVisible = false
        }
    }

    private fun updateTimerDisplay(minutesRemaining: Long, secondsRemaining: Long) {
        binding.tvTime.text = String.format("%02d:%02d", minutesRemaining, secondsRemaining)
    }

    private fun handleTimerFinish(corazones: Int) {
        val updatedCorazones = corazones + 1
        viewModel.updateHeats(updatedCorazones)
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