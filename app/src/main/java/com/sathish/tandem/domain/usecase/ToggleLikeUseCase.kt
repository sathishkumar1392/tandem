package com.sathish.tandem.domain.usecase

import com.sathish.tandem.domain.repo.CommunityRepo

class ToggleLikeUseCase (
    private val repo: CommunityRepo
) {
    suspend operator fun invoke(memberId: String) =
      repo.toggleLike(memberId)
}