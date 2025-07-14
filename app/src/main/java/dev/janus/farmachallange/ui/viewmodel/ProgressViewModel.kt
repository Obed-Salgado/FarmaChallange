package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.domain.GetLevelUseCase
import dev.janus.farmachallange.domain.GetProgressUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(private val getProgress: GetProgressUseCase, private val getLevelUseCase: GetLevelUseCase): ViewModel() {

    private val _showLottie = MutableLiveData<Boolean>()
    val showLottie: LiveData<Boolean> get() = _showLottie
    private val _progress = MutableLiveData<List<Progress>>()
    val progress: LiveData<List<Progress>> get() = _progress
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getLevelsProgress(levels: List<Nivel>){
        viewModelScope.launch {
            _showLottie.postValue(true)
            when(val response = getProgress(getLevelUseCase())){
                is ResponseState.Error -> {
                    _errorMessage.value = response.message
                }
                is ResponseState.Success -> {
                    _progress.value = response.data as List<Progress>
                }
            }
            _showLottie.postValue(false)
        }
    }
}