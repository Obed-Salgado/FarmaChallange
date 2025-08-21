package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repoUsuarios: RepoUsuarios) {
    suspend operator fun invoke(email: String, password: String): ResponseState<String> = repoUsuarios.loginUser(email, password)
}