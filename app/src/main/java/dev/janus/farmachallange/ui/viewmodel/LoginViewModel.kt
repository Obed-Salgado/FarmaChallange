package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.domain.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val login: LoginUseCase) : ViewModel() {

    private val _showLottie = MutableLiveData<Boolean>()
    val showLottie: LiveData<Boolean> get() = _showLottie
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun setUserData(email:String, password: String) {
        viewModelScope.launch {
            _showLottie.postValue(true)
            when(val response = login(email, password)){
                is ResponseState.Error -> {
                    _errorMessage.value = response.message
                }
                is ResponseState.Success -> {
                    _successMessage.value = response.data
                }
                is ResponseState.Loading -> {}
            }
            _showLottie.postValue(false)
        }
    }
}