package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repoUsuarios: RepoUsuarios) {
    operator fun invoke() = repoUsuarios.logOutUser()
}