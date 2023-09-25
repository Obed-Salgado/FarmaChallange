package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repoUsuarios: RepoUsuarios) :ViewModel() {

    fun setUserData(
        nombre: String,
        usuario: String,
        matricula: String,
        email: String,
        password: String,
        urlIcon: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        repoUsuarios.registerUser(nombre, usuario, matricula, email, password, urlIcon, onSuccess, onFailure)
    }
}
