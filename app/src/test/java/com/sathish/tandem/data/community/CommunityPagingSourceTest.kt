package com.sathish.tandem.data.community

import androidx.paging.PagingSource
import com.sathish.tandem.api.CommunityApiService
import com.sathish.tandem.data.community.model.CommunityDTO
import com.sathish.tandem.data.community.model.CommunityMembersDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class CommunityPagingSourceTest {

    private val api: CommunityApiService = mockk()
    private lateinit var  pagingSource: CommunityPagingSource


    @Before
    fun setup(){
        pagingSource = CommunityPagingSource(service = api)
    }

    @Test
    fun `load returns page with nextKey when full page returned` () = runTest {
        val fakeMembers = List(20) { fakeDto(it) }
        coEvery { api.getCommunityList(page = 1) } returns
                Response.success(
                    CommunityDTO(
                        errorCode = null,
                        response = fakeMembers,
                        type = "ok"
                    )
                )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        TestCase.assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        TestCase.assertEquals(20, result.data.size)
        TestCase.assertEquals(2, result.nextKey)
    }

    @Test
    fun `load returns Error when API call fails`() = runTest {
        coEvery { api.getCommunityList(page = 1) } throws IOException("network error")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        TestCase.assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `load returns Error when response body is null`() = runTest {
        coEvery { api.getCommunityList(page = 1) } returns
                Response.success(null)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        TestCase.assertTrue(result is PagingSource.LoadResult.Error)
    }

    private fun fakeDto(id: Int) = CommunityMembersDTO(
        id = id,
        firstName = "Test$id",
        pictureUrl = "",
        natives = listOf("EN"),
        learns = listOf("RU"),
        referenceCnt = 1,
        topic = "test topic"
    )
}