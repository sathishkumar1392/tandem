package com.sathish.tandem.data.community.model

data class CommunityDTO(
    val errorCode: Any?,
    val response: List<CommunityMembersDTO>,
    val type: String
)

data class CommunityMembersDTO(
    val firstName: String,
    val id: Int,
    val learns: List<String>,
    val natives: List<String>,
    val pictureUrl: String,
    val referenceCnt: Int,
    val topic: String
)


