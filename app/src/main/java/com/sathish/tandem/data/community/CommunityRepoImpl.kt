package com.sathish.tandem.data.community

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sathish.tandem.api.CommunityApiService
import com.sathish.tandem.data.local.LikeDao
import com.sathish.tandem.data.local.LikeEntity
import com.sathish.tandem.data.mapper.toDomain
import com.sathish.tandem.domain.model.CommunityMember
import com.sathish.tandem.domain.repo.CommunityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class CommunityRepoImpl(
    private val api : CommunityApiService,
    private val likeDao : LikeDao
): CommunityRepo {

    override fun getCommunity(): Flow<PagingData<CommunityMember>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CommunityPagingSource(service = api)
            }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                dto.toDomain(isLiked = false)
            }
        }
    }

    override suspend fun toggleLike(memberId: String) {
        if (likeDao.isLiked(memberId)) {
            likeDao.unlike(LikeEntity(memberId))
        } else {
            likeDao.like(LikeEntity(memberId))
        }
    }

    override fun getLikedIds(): Flow<Set<String>> = likeDao.getAllLikesIds()
        .map { it.toSet() }
        .distinctUntilChanged()
}