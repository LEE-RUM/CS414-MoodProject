package com.example.myapp01.diary.Database

import androidx.room.*
import com.example.myapp01.Database.DiaryEntity

// DAO interface
@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary_table ORDER BY id DESC") //diaries ordered by descending order
    fun getDiaries(): List<DiaryEntity>? //get

    @Insert
    fun insertDiary(diary: DiaryEntity?) //insert

    @Delete
    fun deleteDiary(diary: DiaryEntity?) //delete

    @Update
    fun updateDiary(diary: DiaryEntity?) //update

}