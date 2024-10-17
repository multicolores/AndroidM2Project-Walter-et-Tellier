package com.example.myapplication.architecture

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.YuGiOhDao
import com.example.myapplication.data.model.YuGiOhEntity


@Database(
    entities = [
        YuGiOhEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class CustomRoomDatabase : RoomDatabase() {
    abstract fun yuGiOhDao(): YuGiOhDao
}
