package dev.janus.farmachallange.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.janus.farmachallange.data.model.InputsError
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.UserRegister
import dev.janus.farmachallange.domain.SetUserDataUseCase
import dev.janus.farmachallange.domain.ValidateInputsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val setUserDataUC: SetUserDataUseCase,
    private val validateInputs: ValidateInputsUseCase
) :ViewModel() {

    private val _showLottie = MutableLiveData<Boolean>()
    val showLottie: LiveData<Boolean> get() = _showLottie
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _errorInputs = MutableLiveData<InputsError>()
    val errorInputs: LiveData<InputsError> get() = _errorInputs

    fun setUserData(
        user: UserRegister
    ) {
        if(validateForm(user))
            return
        viewModelScope.launch {
            _showLottie.value = true
            when(val response = setUserDataUC(user)){
                is ResponseState.Error -> {
                    _errorMessage.value = response.message
                }
                is ResponseState.Success -> {
                    _successMessage.value = response.data
                }
                is ResponseState.Loading -> {}
            }
            _showLottie.value = false
        }
    }

    private fun validateForm(user: UserRegister): Boolean{
        val validate = validateInputs.invoke(user)
        _errorInputs.value = validate
        with(validate){
            return nameError || userNameError || tuitionError || emailError || passwordError || urlIconError
        }
    }
}