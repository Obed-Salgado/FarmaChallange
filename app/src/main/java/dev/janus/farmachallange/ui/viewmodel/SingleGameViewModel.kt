package dev.janus.farmachallange.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.data.network.RepoEstadistica
import dev.janus.farmachallange.data.network.RepoUsuarios
import dev.janus.farmachallange.domain.getQuestionDataUseCase
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleGameViewModel @Inject constructor(
    private val getQuestionDataUseCase: getQuestionDataUseCase,
    private val repoStatus: RepoEstadistica
): ViewModel() {

   val _pregunta = MutableLiveData<Pregunta>()

    fun fetchQuestions(idNivel:String, idRonda:String) {
        viewModelScope.launch {
            val pregunta = getQuestionDataUseCase(idNivel,idRonda)
            if (pregunta != null){
                _pregunta.postValue(pregunta!!)
            }
        }
    }

    fun updateHeats(hearts:Int){
        repoStatus.uptdateHearts(UserManager.getInstanceUser().id,  hearts)

    }

    fun updateCoins(coins: Int) {
        repoStatus.uptdateCoins(UserManager.getInstanceUser().id, coins)
    }
}