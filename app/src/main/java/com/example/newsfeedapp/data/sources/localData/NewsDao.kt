package com.example.newsfeedapp.data.sources.localData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsfeedapp.data.model.Article


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( article: Article): Long



    @Query("SELECT * FROM  Article")
     fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM Article")
    fun deleteAllArticle()



}