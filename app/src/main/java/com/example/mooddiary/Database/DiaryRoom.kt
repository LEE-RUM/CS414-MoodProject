package com.example.myapp01.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp01.diary.Database.DiaryDao

//room class
@Database(entities = [DiaryEntity::class], version = 2)
abstract class DiaryRoom : RoomDatabase() {


    abstract fun diaryDao(): DiaryDao?

    companion object {
        private var INSTANCE: DiaryRoom? = null

        fun getAppDatabase(context: Context): DiaryRoom? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<DiaryRoom>(
                    context.applicationContext, DiaryRoom::class.java, "DiaryDb"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }
    }
}