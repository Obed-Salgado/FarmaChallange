package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repoUsuarios: RepoUsuarios) : ViewModel() {

    fun setUserData(email:String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        repoUsuarios.loginUser(email, password, onSuccess, onFailure )
    }
}