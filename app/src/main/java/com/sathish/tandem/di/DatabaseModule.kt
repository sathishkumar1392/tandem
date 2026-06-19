package com.sathish.tandem.di

import androidx.room.Room
import com.sathish.tandem.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "tandem_db"
        ).build()
    }
    single { get<AppDatabase>().likeDao() }
}