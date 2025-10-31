package dev.janus.farmachallange.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.databinding.ActivityGameBinding
import dev.janus.farmachallange.ui.view.fragments.MenuFragment
import dev.janus.farmachallange.ui.viewmodel.GameActivityViewModel
import dev.janus.farmachallange.utils.Constants.SHARED_LEVELS_KEY
import dev.janus.farmachallange.utils.Constants.TIME_OF_TIMER_HEART
import dev.janus.farmachallange.utils.UserManager
import dev.janus.farmachallange.utils.clases.NetworkAvailable
import dev.janus.farmachallange.utils.clases.Timer
import dev.janus.farmachallange.utils.loadImage
import java.util.ArrayList

@AndroidEntryPoint
class GameActivity : AppCompatActivity(), MenuFragment.SharedLevels {

    private lateinit var binding: ActivityGameBinding
    private val viewModel: GameActivityViewModel by viewModels()
    private lateinit var timer: Timer
    private val networkAvailable: NetworkAvailable = NetworkAvailable()
    private var levels: List<Level>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (networkAvailable.isNetworkAvailable(this)) {
            timer = Timer(TIME_OF_TIMER_HEART)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragmenttMenu) as NavHostFragment
            val navController = navHostFragment.navController

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.singleGameFragment) {
                    binding.navigationView.visibility = View.GONE
                } else {
                    binding.navigationView.visibility = View.VISIBLE
                }
            }

            binding.navigationView.selectedItemId = R.id.inicio

            binding.navigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.inicio -> binding.navHostFragmenttMenu.findNavController()
                        .navigate(R.id.menuFragment)

                    R.id.perfil -> binding.navHostFragmenttMenu.findNavController()
                        .navigate(R.id.profileFragment, bundleOf().apply {
                            putParcelableArrayList(SHARED_LEVELS_KEY, levels as ArrayList<out Parcelable?>?)
                        })

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
            if(!timer.isTimerInProgress())
                startOrCancelTimer(user.corazones)
        }
    }

    private fun startOrCancelTimer(hearts: Int) {
        val isTimerVisible = hearts < 12
        if (isTimerVisible) {
            timer.startTempHearts(
                onTick = { minutesRemaining, secondsRemaining ->
                    updateTimerDisplay(minutesRemaining, secondsRemaining)
                },
                onFinish = {
                    handleTimerFinish()
                }
            )
        } else {
            binding.tvTime.text = ""
        }
    }

    private fun updateTimerDisplay(minutesRemaining: Long, secondsRemaining: Long) {
        binding.tvTime.text = String.format("%02d:%02d", minutesRemaining, secondsRemaining)
    }

    private fun handleTimerFinish() {
        UserManager.incrementHearts()
        viewModel.updateHearts(UserManager.getHearts())
    }

    private fun updateUserInfo(user: Usuario?) {
        if (user != null) {
            UserManager.setUser(user)
            binding.tvName.text = user.usuario
            binding.tvCorazon.text = user.corazones.toString()
            binding.tvMoneda.text = user.monedas.toString()
            binding.ivIconUser.loadImage(user.urlIcon)
        } else {
            Toast.makeText(this, "No hay datos de usuario", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSharedLevels(levels: List<Level>) {
        this.levels = levels
    }
}