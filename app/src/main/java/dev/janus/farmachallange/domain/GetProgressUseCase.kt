package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Level
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.network.RepoProgreso
import javax.inject.Inject

class GetProgressUseCase @Inject constructor(private val repoProgreso: RepoProgreso) {
    suspend operator fun invoke(levels: List<Level>): ResponseState<List<Progress>> = repoProgreso.getLevelProgress(levels)
}