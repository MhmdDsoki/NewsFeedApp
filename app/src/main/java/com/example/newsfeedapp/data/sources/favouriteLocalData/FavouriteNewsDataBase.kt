package com.example.newsfeedapp.data.sources.favouriteLocalData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeedapp.data.model.Article

@Database(entities = [Article::class], version = 1 , exportSchema = false)

abstract class FavouriteNewsDataBase : RoomDatabase() {
    abstract fun getFavouriteDao(): FavouriteNewsDao
}