package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.domain.getLevelUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val getLevelUseCase: getLevelUseCase):ViewModel() {
    private val _nameLevel = MutableLiveData<List<Nivel>>()
    val nameLevel:LiveData<List<Nivel>> get() = _nameLevel
    init {
        viewModelScope.launch {
            val nivel  = getLevelUseCase()
            if (!nivel.isNullOrEmpty()){
                _nameLevel.postValue(nivel)
            }
            else{

            }

        }
    }
}