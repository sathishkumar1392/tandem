package com.sathish.tandem.api

import com.sathish.tandem.data.community.model.CommunityDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApiService {

    @GET("/api/community_{page}.json")
    suspend fun getCommunityList(@Path("page") page: Int) : Response<CommunityDTO>
}