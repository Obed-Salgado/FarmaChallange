package dev.janus.farmachallange.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import dev.janus.farmachallange.data.network.RepoEstadistica
import dev.janus.farmachallange.domain.GetUserUseCase
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class GameActivityViewModel @Inject constructor(private val getUserUseCase:GetUserUseCase, private val repoStatus: RepoEstadistica) :ViewModel() {

        val fetchUser = liveData(Dispatchers.IO) {
            getUserUseCase().collect{
                emit(it)
            }
        }
        fun updateHeats(hearts: Int) {
            repoStatus.uptdateHearts(UserManager.getInstanceUser().id, hearts)
        }

}