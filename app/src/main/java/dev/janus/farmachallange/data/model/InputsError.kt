package dev.janus.farmachallange.data.model

data class InputsError(
    var nameError: Boolean = false,
    var userNameError: Boolean = false,
    var tuitionError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var urlIconError: Boolean = false
)
