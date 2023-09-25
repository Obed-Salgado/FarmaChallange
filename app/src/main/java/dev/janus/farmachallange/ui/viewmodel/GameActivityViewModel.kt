package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.data.network.RepoEstadistica
import dev.janus.farmachallange.data.network.RepoUsuarios
import dev.janus.farmachallange.utils.UserManager
import javax.inject.Inject

@HiltViewModel
class GameActivityViewModel @Inject constructor(private val repoUser: RepoUsuarios, private val repoStatus: RepoEstadistica) :ViewModel() {

    fun fetchUser(): LiveData<Usuario?> {
        val userDataLiveData = MutableLiveData<Usuario?>()
        val liveData =  repoUser.getUserData(UserManager.getInstanceUser().id)
        liveData.observeForever { usuario ->
            userDataLiveData.value = usuario
        }
        return userDataLiveData
    }

    fun updateHeats(hearts:Int){
        repoStatus.uptdateHearts(UserManager.getInstanceUser().id, hearts)
    }
}