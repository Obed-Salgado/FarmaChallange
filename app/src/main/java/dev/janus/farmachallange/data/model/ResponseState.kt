package dev.janus.farmachallange.data.model

sealed class ResponseState {
    class Error(val message: String) : ResponseState()
    class Success(val data: Any): ResponseState()
}
