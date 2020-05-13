package com.example.newsfeedapp.data.sources.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeedapp.data.model.Article

@Database(entities = [Article::class], version = 1)

abstract class NewsDataBase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}