package com.example.newsfeedapp.data.sources.remoteApi


import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.flow.Flow


interface IOnlineDataSource {
   suspend fun getArticles(): List<Article>
}