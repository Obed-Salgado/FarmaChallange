package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.domain.GetRondaUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RondasViewModel @Inject constructor(private val getRondaUseCase: GetRondaUseCase) :ViewModel() {

    private val _showLottie = MutableLiveData<Boolean>()
    val showLottie: LiveData<Boolean> get() = _showLottie
    private val _nameRonda = MutableLiveData<List<Ronda>>()
    val nameRonda:LiveData<List<Ronda>> get() = _nameRonda

    fun getRonda(idRonda:String) {
        viewModelScope.launch {
            _showLottie.postValue(true)
            when(val response = getRondaUseCase(idRonda)){
                is ResponseState.Error -> response.message
                is ResponseState.Loading -> {}
                is ResponseState.Success -> _nameRonda.postValue(response.data)
            }
            _showLottie.postValue(false)
        }
    }

}