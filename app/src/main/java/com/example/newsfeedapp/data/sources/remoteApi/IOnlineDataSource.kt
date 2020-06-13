package com.example.newsfeedapp.data.sources.remoteApi


import com.example.newsfeedapp.data.model.Article

interface IOnlineDataSource {
   suspend fun getArticles(): List<Article> = emptyList()
}