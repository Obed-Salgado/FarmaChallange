package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.network.RepoEstadistica
import dev.janus.farmachallange.domain.getQuestionDataUseCase
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleGameViewModel @Inject constructor(
    private val getQuestionDataUseCase: getQuestionDataUseCase,
    private val repoStatus: RepoEstadistica
) : ViewModel() {

    private val _pregunta = MutableLiveData<Pregunta>()
    val pregunta: LiveData<Pregunta> get() = _pregunta
    private val preguntasYaSeleccionadas =
        mutableListOf<Pregunta>() // Lista para almacenar preguntas ya seleccionadas
    private val _isVisible = MutableLiveData<Boolean>()
    val isVisible: LiveData<Boolean> get() = _isVisible
    private val _numberquest = MutableLiveData<String>()
    val numberquest: LiveData<String> get() = _numberquest




    fun fetchQuestions(
        idNivel: String,
        idRonda: String,
        currentIndex: Int,
        overRonda: () -> Unit
    ) {
        viewModelScope.launch {
            _isVisible.postValue(true)
            val preguntaList = getQuestionDataUseCase(idNivel, idRonda)
            if (currentIndex >= 0 && currentIndex < preguntaList.size) {
                var preguntaActual = preguntaList[currentIndex]
                // Verificar si la pregunta ya se seleccionó previamente
                while (preguntasYaSeleccionadas.contains(preguntaActual)) {
                    preguntaActual = preguntaList.random()
                }

                preguntasYaSeleccionadas.add(preguntaActual!!) // Agregar la pregunta actual a la lista de preguntas ya seleccionadas
                _isVisible.postValue(false)
                _numberquest.postValue("${currentIndex}/${preguntaList.size}")
                _pregunta.postValue(preguntaActual!!)
            } else if (currentIndex == preguntaList.size) {
                _isVisible.postValue(false)
                _numberquest.postValue("${currentIndex}/${preguntaList.size}")
                overRonda()
            }
        }
    }



fun updateHearts(hearts: Int) {
    repoStatus.uptdateHearts(UserManager.getInstanceUser().id, hearts)

}

fun updateCoins(coins: Int) {
    repoStatus.uptdateCoins(UserManager.getInstanceUser().id, coins)
}
}