package com.sathish.tandem.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class LikeEntity(
    @PrimaryKey val memberId:String
)