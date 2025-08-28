package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.network.RepoUsuarios
import javax.inject.Inject

class GetExistSessionUseCase @Inject constructor(private val userRepo: RepoUsuarios) {
    operator fun invoke(): Boolean = userRepo.checkExistSession()
}