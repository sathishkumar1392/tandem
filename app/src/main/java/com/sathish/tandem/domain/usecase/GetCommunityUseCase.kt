package com.sathish.tandem.domain.usecase

import androidx.paging.PagingData
import com.sathish.tandem.domain.model.CommunityMember
import com.sathish.tandem.domain.repo.CommunityRepo
import kotlinx.coroutines.flow.Flow

class GetCommunityUseCase (private val repo: CommunityRepo) {
    operator fun invoke(): Flow<PagingData<CommunityMember>> =
        repo.getCommunity()

}