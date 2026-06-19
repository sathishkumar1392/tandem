package com.sathish.tandem.data.community

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sathish.tandem.api.CommunityApiService
import com.sathish.tandem.data.community.model.CommunityMembersDTO


class CommunityPagingSource (
   private val service: CommunityApiService,
): PagingSource<Int, CommunityMembersDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityMembersDTO> {
        val page = params.key ?: 1

        return try {
            val response = service.getCommunityList(page = page)
            if (!response.isSuccessful) {
                return LoadResult.Error(Exception(response.message()))
            }
            val body = response.body()
                ?: return LoadResult.Error(Exception("Body is null"))


            LoadResult.Page(
                data = body.response,
                prevKey = null,
                nextKey =  if (body.response.size < 20) null else page + 1
                )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityMembersDTO>): Int? {
        return state.anchorPosition
    }

}