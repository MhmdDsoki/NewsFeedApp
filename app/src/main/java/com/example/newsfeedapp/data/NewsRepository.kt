package com.example.newsfeedapp.data


import androidx.lifecycle.LiveData
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.model.NewsResponse
import com.example.newsfeedapp.data.sources.localData.NewsDao
import com.example.newsfeedapp.data.sources.remoteApi.ApiService


class NewsRepository(private val service: ApiService, private val dao: NewsDao) : ApiService,
    NewsDao {


    // get all articles from api
    override suspend fun getArticlesNews(sourceName: String): NewsResponse =
        service.getArticlesNews(sourceName)

    override suspend fun insert(article: Article): Long = dao.insert(article)


    // get favourite articles from cache
    override fun getAllArticles(): LiveData<List<Article>> = dao.getAllArticles()

    override suspend fun deleteArticle(article: Article) = dao.deleteArticle(article)

    override suspend fun deleteAllArticle() = dao.deleteAllArticle()

    override fun isFavorite(articleUrl: String): Int = dao.isFavorite(articleUrl)
}