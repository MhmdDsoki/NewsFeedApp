package com.example.newsfeedapp.data.sources.homeCahedData

import com.example.newsfeedapp.data.model.Article


class OfflineSourcesRoomBased(private val homeDao: HomeNewsDao) : IOfflineDataSource {

    override fun getArticles(): List<Article> = homeDao.getAllArticles()

    override suspend fun cacheArticles(data: List<Article>) {
        homeDao.insertList(data)
    }

    override suspend fun deleteAllNews() {
        homeDao.deleteAllNews()
    }
}