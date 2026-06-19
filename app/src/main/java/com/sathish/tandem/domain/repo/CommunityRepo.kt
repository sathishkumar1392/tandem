package com.sathish.tandem.domain.repo

import androidx.paging.PagingData
import com.sathish.tandem.domain.model.CommunityMember
import kotlinx.coroutines.flow.Flow

interface CommunityRepo {
     fun getCommunity(): Flow<PagingData<CommunityMember>>
    suspend fun toggleLike(memberId : String)
    fun getLikedIds(): Flow<Set<String>>
}