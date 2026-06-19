package com.sathish.tandem.domain.usecase

import com.sathish.tandem.domain.repo.CommunityRepo
import kotlinx.coroutines.flow.Flow

class GetLikedIdsUseCase(private val repo: CommunityRepo) {
    operator fun invoke(): Flow<Set<String>> = repo.getLikedIds()
}