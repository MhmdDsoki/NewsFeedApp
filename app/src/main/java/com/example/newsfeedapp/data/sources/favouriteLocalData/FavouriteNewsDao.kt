package com.example.newsfeedapp.data.sources.favouriteLocalData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsfeedapp.data.model.Article


@Dao
interface FavouriteNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM  Article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM Article")
    suspend fun deleteAllArticle()

    @Query("SELECT EXISTS (SELECT 1 FROM Article WHERE url=:articleUrl)")
    fun isFavorite(articleUrl: String): Int

}