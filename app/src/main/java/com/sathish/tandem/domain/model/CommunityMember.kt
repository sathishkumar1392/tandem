package com.sathish.tandem.domain.model

data class CommunityMember(
    val id: Int,
    val firstName: String,
    val pictureUrl: String,
    val nativeLanguage: String,
    val learnsLanguage: String,
    val referenceCnt: Int,
    val topic: String,
    val isNew: Boolean,
    val isLiked: Boolean
)