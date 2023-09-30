package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.domain.GetRondaUseCase
import dev.janus.farmachallange.domain.getQuestionDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RondasViewModel @Inject constructor(private val getRondaUseCase: GetRondaUseCase) :ViewModel() {

    private val _nameRonda = MutableLiveData<List<Ronda>>()
    val nameRonda:LiveData<List<Ronda>> get() = _nameRonda

    fun getRonda(idRonda:String) {
        viewModelScope.launch {
            val ronda = getRondaUseCase(idRonda)
           if (!ronda.isNullOrEmpty())
               _nameRonda.postValue(ronda)
        }
    }

}