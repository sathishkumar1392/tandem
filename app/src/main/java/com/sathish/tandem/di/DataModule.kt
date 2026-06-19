package com.sathish.tandem.di

import com.sathish.tandem.data.community.CommunityRepoImpl
import com.sathish.tandem.domain.repo.CommunityRepo
import com.sathish.tandem.domain.usecase.GetCommunityUseCase
import com.sathish.tandem.domain.usecase.GetLikedIdsUseCase
import com.sathish.tandem.domain.usecase.ToggleLikeUseCase
import org.koin.dsl.module

val dataModule = module {
        single<CommunityRepo> {
            CommunityRepoImpl(get(),get())
        }

    factory { GetCommunityUseCase(get()) }
    factory { ToggleLikeUseCase(repo = get()) }
    factory { GetLikedIdsUseCase(repo = get()) }
}