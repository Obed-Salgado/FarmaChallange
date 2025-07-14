package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.network.RepoEstadistica
import dev.janus.farmachallange.domain.GetQuestionDataUseCase
import dev.janus.farmachallange.domain.setIncorrectAnswerUseCase
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleGameViewModel @Inject constructor(
    private val getQuestionDataUseCase: GetQuestionDataUseCase,
    private val setIncorrectAnswerUseCase: setIncorrectAnswerUseCase,
    private val repoStatus: RepoEstadistica
) : ViewModel() {

    private val _question = MutableLiveData<Pregunta>()
    val question: LiveData<Pregunta> get() = _question
    private val _questions = MutableLiveData<List<Pregunta>>()
    private val _numberOfQuestions = MutableLiveData<Int>()
    val numberOfQuestions: LiveData<Int>get() = _numberOfQuestions
    private val _numberquest = MutableLiveData<String>()
    val numberquest: LiveData<String> get() = _numberquest
    private val _finishGame = MutableLiveData<Boolean>()
    val finishGame: LiveData<Boolean> get() = _finishGame

    fun getAllQuestions(idNivel: String, idRonda: String){
        viewModelScope.launch {
            val temp = getQuestionDataUseCase.invoke(idNivel, idRonda).shuffled()
            _questions.value = temp
            _numberOfQuestions.value = _questions.value?.size ?: 0
        }
    }

    fun getQuestion(progressQuestions: Int){
        viewModelScope.launch {
            if(_questions.value?.isNotEmpty() == true) {
                val temp = _questions.value?.last() ?: Pregunta()
                _question.postValue(temp)
                _questions.value = _questions.value?.subList(0, (_questions.value?.size ?: 1) - 1)
                _numberquest.postValue("${progressQuestions}/${numberOfQuestions.value}")
            } else {
                _numberquest.postValue("${progressQuestions}/${numberOfQuestions.value}")
                _finishGame.value = true
            }
        }
    }

    fun updateHearts(hearts: Int) {
        repoStatus.uptdateHearts(UserManager.getInstanceUser().id, hearts)
    }

    fun updateCoins(coins: Int) {
        repoStatus.uptdateCoins(UserManager.getInstanceUser().id, coins)
    }

    fun setWrongAnswer(nivel: Int, ronda: Int, numPregunta: Int, respuesta: String, pregunta: String){
        setIncorrectAnswerUseCase(nivel, ronda, numPregunta, respuesta, pregunta)
    }
}