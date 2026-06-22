package com.sathish.tandem.feature.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sathish.tandem.domain.model.CommunityMember
import com.sathish.tandem.domain.usecase.GetCommunityUseCase
import com.sathish.tandem.domain.usecase.GetLikedIdsUseCase
import com.sathish.tandem.domain.usecase.ToggleLikeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CommunityViewModel(
    getCommunityUseCase: GetCommunityUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    getLikedIdsUseCase: GetLikedIdsUseCase
): ViewModel() {
    val community: Flow<PagingData<CommunityMember>> =
        getCommunityUseCase().cachedIn(viewModelScope)

    val likedIds: StateFlow<Set<String>> =
        getLikedIdsUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun onLikedClick(memberId: String) {
        viewModelScope.launch {
            toggleLikeUseCase(memberId)
        }
    }
}