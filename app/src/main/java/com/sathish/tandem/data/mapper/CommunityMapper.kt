package com.sathish.tandem.data.mapper

import com.sathish.tandem.data.community.model.CommunityMembersDTO
import com.sathish.tandem.domain.model.CommunityMember

fun CommunityMembersDTO.toDomain(isLiked: Boolean) : CommunityMember {
    return CommunityMember(
        id = id,
        firstName = firstName,
        pictureUrl = pictureUrl,
        nativeLanguage = natives.firstOrNull() ?: "",
        learnsLanguage = learns.firstOrNull() ?: "",
        referenceCnt = referenceCnt,
        topic = topic,
        isNew = referenceCnt == 0,
        isLiked = isLiked
    )
}
