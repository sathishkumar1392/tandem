package com.sathish.tandem.feature

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sathish.tandem.domain.usecase.GetCommunityUseCase
import com.sathish.tandem.domain.usecase.GetLikedIdsUseCase
import com.sathish.tandem.domain.usecase.ToggleLikeUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommunityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getCommunityUseCase: GetCommunityUseCase = mockk()
    private val toggleLikeUseCase: ToggleLikeUseCase = mockk()
    private val getLikedIdsUseCase: GetLikedIdsUseCase = mockk()

    private lateinit var viewModel: CommunityViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getLikedIdsUseCase() } returns flowOf(emptySet())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onLikedClick calls toggleLikeUseCase with correct id`() = runTest {
        coEvery { toggleLikeUseCase("1") } just Runs
        every { getCommunityUseCase() } returns flowOf(PagingData.Companion.empty())

        viewModel = CommunityViewModel(getCommunityUseCase, toggleLikeUseCase, getLikedIdsUseCase)

        viewModel.onLikedClick("1")
        advanceUntilIdle()

        coVerify(exactly = 1) { toggleLikeUseCase("1") }
    }

    @Test
    fun `likedIds emits initial empty set`() = runTest {
        every { getCommunityUseCase() } returns flowOf(PagingData.Companion.empty())

        viewModel = CommunityViewModel(getCommunityUseCase, toggleLikeUseCase, getLikedIdsUseCase)

        viewModel.likedIds.test {
            TestCase.assertEquals(emptySet<String>(), awaitItem())
        }
    }
}