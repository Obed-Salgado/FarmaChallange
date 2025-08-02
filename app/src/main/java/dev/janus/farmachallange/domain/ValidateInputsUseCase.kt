package dev.janus.farmachallange.domain

import android.util.Patterns
import dev.janus.farmachallange.data.model.InputsError
import dev.janus.farmachallange.data.model.UserRegister
import javax.inject.Inject

class ValidateInputsUseCase @Inject constructor() {
    operator fun invoke(user: UserRegister): InputsError {
        return InputsError().apply {
            nameError = user.name.isEmpty()
            userNameError = user.userName.isEmpty()
            tuitionError = user.tuition.isEmpty() || user.tuition.length < 5
            emailError = user.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(user.email.trim()).matches()
            passwordError = user.password.isEmpty() || user.password.length < 8
            urlIconError = user.urlIcon.isEmpty()
        }
    }
}

/*
    *Nombre +
     -Solo letras
     -60 caracteres

    *Usuario +
     -letras y numeros
     -20 caracteres

    *Matricula +
     -Solo numeros
     -10 caracteres
     -minimo 5 caracteres

    *Correo +
     -formato de correo
     -50 caracteres

    *Contraseña +
     -8 a 20 caracteres
*/