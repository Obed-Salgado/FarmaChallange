package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.UserRegister
import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

class SetUserDataUseCase @Inject constructor(private val repoUsuarios: RepoUsuarios) {
    suspend operator fun invoke(user: UserRegister): ResponseState<String> = repoUsuarios.registerUser(user)
}