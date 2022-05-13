package com.example.myapp01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapp01.Database.DiaryRoom
import com.example.myapp01.Database.DiaryEntity


class DiaryViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var allUsers: MutableLiveData<List<DiaryEntity>>

    init {
        allUsers = MutableLiveData()
        getAllDiary()
    }

    //observer for diary
    fun diaryObserver(): MutableLiveData<List<DiaryEntity>> {
        return allUsers
    }

    //get all the diaries, called in the insert, delete, update functions
    fun getAllDiary() {
        val diaryD = DiaryRoom.getAppDatabase((getApplication()))?.diaryDao()
        val list = diaryD?.getDiaries()

        allUsers.postValue(list)
    }

    //function to insert diaries
    fun diaryInsert(entity: DiaryEntity) {
        val diaryD = DiaryRoom.getAppDatabase(getApplication())?.diaryDao()
        diaryD?.insertDiary(entity)
        getAllDiary()
    }

    //function to delete diaries
    fun diaryDelete(entity: DiaryEntity) {
        val diaryD = DiaryRoom.getAppDatabase(getApplication())?.diaryDao()
        diaryD?.deleteDiary(entity)
        getAllDiary()
    }

    //function to update diaries
    fun diaryUpdate(entity: DiaryEntity) {
        val diaryD = DiaryRoom.getAppDatabase(getApplication())?.diaryDao()
        diaryD?.updateDiary(entity)
        getAllDiary()
    }

}