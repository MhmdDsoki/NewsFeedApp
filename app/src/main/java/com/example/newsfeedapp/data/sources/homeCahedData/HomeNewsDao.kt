package com.example.newsfeedapp.data.sources.homeCahedData

import androidx.room.*
import com.example.newsfeedapp.data.model.Article


@Dao
interface HomeNewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList( article: List<Article>):List<Long>


    @Query("SELECT * FROM  Article")
    fun getAllArticles(): List<Article>

    @Query("DELETE FROM Article")
    suspend fun deleteAllNews()


}