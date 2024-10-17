package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "yu_gi_oh_table")
data class YuGiOhEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "atk")
    val atk: Int,

    @ColumnInfo(name = "def")
    val def: Int,

    @ColumnInfo(name = "current_timestamp")
    val current_timestamp: String,

    @ColumnInfo(name = "cardImageUrl")
    val cardImageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
