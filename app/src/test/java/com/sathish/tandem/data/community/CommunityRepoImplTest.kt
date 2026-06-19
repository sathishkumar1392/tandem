package com.sathish.tandem.data.community

import app.cash.turbine.test
import com.sathish.tandem.api.CommunityApiService
import com.sathish.tandem.data.local.LikeDao
import com.sathish.tandem.data.local.LikeEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CommunityRepoImplTest {

    private val api: CommunityApiService = mockk()
    private val likeDao: LikeDao = mockk()
    private lateinit var repo: CommunityRepoImpl

    @Before
    fun setup() {
        repo = CommunityRepoImpl(api, likeDao)
    }

    @Test
    fun `toggleLike inserts like when not already liked`() = runTest {
        coEvery { likeDao.isLiked("1") } returns false
        coEvery { likeDao.like(any()) } just Runs

        repo.toggleLike("1")

        coVerify(exactly = 1) { likeDao.like(LikeEntity("1")) }
        coVerify(exactly = 0) { likeDao.unlike(any()) }
    }

    @Test
    fun `toggleLike removes like when already liked`() = runTest {
        coEvery { likeDao.isLiked("1") } returns true
        coEvery { likeDao.unlike(any()) } just Runs

        repo.toggleLike("1")

        coVerify(exactly = 1) { likeDao.unlike(LikeEntity("1")) }
        coVerify(exactly = 0) { likeDao.like(any()) }
    }

    @Test
    fun `getLikedIds emits set from dao`() = runTest {
        every { likeDao.getAllLikesIds() } returns flowOf(listOf("1", "2"))

        repo.getLikedIds().test {
            val result = awaitItem()
            TestCase.assertEquals(setOf("1", "2"), result)
            awaitComplete()
        }
    }
}