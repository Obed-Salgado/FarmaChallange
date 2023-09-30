package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.data.network.RepoUsuarios
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repoUsuarios: RepoUsuarios) {
    suspend operator fun invoke():Flow<Usuario> = repoUsuarios.getUserData(UserManager.getInstanceUser().id)
}