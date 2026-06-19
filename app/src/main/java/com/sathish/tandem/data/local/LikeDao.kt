package com.sathish.tandem.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun like(entity: LikeEntity)

    @Delete
    suspend fun unlike(entity: LikeEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE memberId = :memberId)")
    suspend fun isLiked(memberId:String) : Boolean

    @Query("SELECT memberId FROM likes")
    fun getAllLikesIds(): Flow<List<String>>
}