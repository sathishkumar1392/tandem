package com.sathish.tandem

import android.app.Application
import com.sathish.tandem.di.dataModule
import com.sathish.tandem.di.databaseModule
import com.sathish.tandem.di.networkModule
import com.sathish.tandem.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CommunityApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        configDi()
    }

    private fun configDi() {
        startKoin {
            androidContext(this@CommunityApplication)
            androidLogger(Level.DEBUG)
            modules(networkModule, databaseModule,dataModule,viewModel)
        }
    }
}