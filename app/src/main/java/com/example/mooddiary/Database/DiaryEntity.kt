package com.example.myapp01.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_table") //table name can be found in database
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0, //id
    @ColumnInfo(name = "title") val title: String, //title of diary
    @ColumnInfo(name = "content") val content: String //content of diary
)
