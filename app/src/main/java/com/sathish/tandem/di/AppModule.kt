package com.sathish.tandem.di

import com.sathish.tandem.feature.community.CommunityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModel = module {
    viewModelOf(::CommunityViewModel)

}