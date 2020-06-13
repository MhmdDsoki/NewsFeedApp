package com.example.newsfeedapp.data.sources.remoteApi

import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/articles")
    suspend fun getArticlesNews(@Query("source") sourceName: String): NewsResponse
}