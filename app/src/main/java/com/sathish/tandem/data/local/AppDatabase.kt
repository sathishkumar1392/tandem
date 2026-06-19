package com.sathish.tandem.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likeDao(): LikeDao
}