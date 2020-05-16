package com.example.newsfeedapp.data


import androidx.lifecycle.MutableLiveData
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.sources.homeCahedData.HomeNewsDao
import com.example.newsfeedapp.data.sources.remoteApi.ApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class NewsRepository(private val service: ApiService, private val homeDao: HomeNewsDao) {

    val articlesNews: MutableLiveData<Resource<Article>> = MutableLiveData()

    suspend fun getArticles() {
        articlesNews.postValue(Resource.Loading())
        try {

            // coroutineScope is needed, else in case of any network error, it will crash
            coroutineScope {
                val articleSourceTheNextWeb = async { service.getArticlesNews("the-next-web") }
                val articleSourceAssociatedPress =
                    async { service.getArticlesNews("associated-press") }
                val firstSource = articleSourceTheNextWeb.await()
                val secondSource = articleSourceAssociatedPress.await()
                val allArticlesFromApi = mutableListOf<Article>()
                firstSource.articles?.let { allArticlesFromApi.addAll(it) }
                secondSource.articles?.let { allArticlesFromApi.addAll(it) }
                articlesNews.postValue(Resource.Success(allArticlesFromApi))

                // cache to room
                insertListToRoom(allArticlesFromApi)

            }

        } catch (e: Exception) {
            articlesNews.postValue(
                Resource.Error(
                    data = getBreakingNewsFromRoom(),
                    msg = e.message
                )
            )
        }
    }

    private suspend fun insertListToRoom(article: List<Article>): List<Long> =
        homeDao.insertList(article)

    fun getBreakingNewsFromRoom(): List<Article> = homeDao.getAllArticles()

}