package com.example.newsfeedapp.data.sources.homeCahedData

import com.example.newsfeedapp.data.model.Article

interface IOfflineDataSource {
     fun getArticles(): List<Article> = emptyList()

    suspend fun cacheArticles(data: List<Article>){}

    suspend fun deleteAllNews(){}



}
