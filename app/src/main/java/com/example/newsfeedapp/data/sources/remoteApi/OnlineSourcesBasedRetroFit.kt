package com.example.newsfeedapp.data.sources.remoteApi


import android.util.Log
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class OnlineSourcesBasedRetroFit (private val service: ApiService)  :
    IOnlineDataSource {

    private val articlesNews = mutableListOf<Article>()
companion object {
     var errorMsg:String=""
}


    override suspend fun getArticles(): List<Article> {

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
                articlesNews.addAll(allArticlesFromApi)
            }
        } catch (e: Exception) {
            errorMsg= e.message.toString()
            Log.e("TAG", errorMsg)

        }
        return articlesNews
    }

}