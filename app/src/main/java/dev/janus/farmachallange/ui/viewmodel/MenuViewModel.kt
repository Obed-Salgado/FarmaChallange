package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.domain.GetLevelUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val getLevelUseCase: GetLevelUseCase): ViewModel() {

    private val _showLottie = MutableLiveData<Boolean>()
    val showLottie: LiveData<Boolean> get() = _showLottie
    private val _levels = MutableLiveData<List<Level>>()
    val levels:LiveData<List<Level>> get() = _levels

    init {
        viewModelScope.launch {
            _showLottie.postValue(true)
            when(val response  = getLevelUseCase()){
                is ResponseState.Error -> response.message
                is ResponseState.Loading -> {}
                is ResponseState.Success -> _levels.postValue(response.data)
            }
            _showLottie.postValue(false)
        }
    }
}