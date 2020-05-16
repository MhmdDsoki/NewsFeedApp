package com.example.newsfeedapp.data

import androidx.lifecycle.LiveData
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.sources.favouriteLocalData.FavouriteNewsDao

class FavRepo(private val favDao: FavouriteNewsDao) : FavouriteNewsDao {
    override suspend fun insert(article: Article): Long = favDao.insert(article)

    override fun getAllArticles(): LiveData<List<Article>> = favDao.getAllArticles()

    override suspend fun deleteArticle(article: Article) = favDao.deleteArticle(article)

    override suspend fun deleteAllArticle() = favDao.deleteAllArticle()

    override fun isFavorite(articleUrl: String): Int = favDao.isFavorite(articleUrl)
}