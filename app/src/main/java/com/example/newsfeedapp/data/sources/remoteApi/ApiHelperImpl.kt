package com.example.newsfeedapp.data.sources.remoteApi

import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : IApiHelper {

    override fun getarticles(source: String)= flow { emit(apiService.getArticlesNews(source)) }
}
