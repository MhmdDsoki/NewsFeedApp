package com.example.newsfeedapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val articleNews = MutableLiveData<Resource<Article>>()

    init {
        fetchArticlesNewsFromApi()
    }

    private fun fetchArticlesNewsFromApi() = viewModelScope.launch {
        articleNews.postValue(Resource.Loading())
        try {
            // coroutineScope is needed, else in case of any network error, it will crash
            coroutineScope {
                val articleSourceTheNextWeb = async { repository.getArticlesNews("the-next-web") }
                val articleSourceAssociatedPress =
                    async { repository.getArticlesNews("associated-press") }
                val firstSource = articleSourceTheNextWeb.await()
                val secondSource = articleSourceAssociatedPress.await()
                val allArticlesFromApi = mutableListOf<Article>()
                firstSource.articles?.let { allArticlesFromApi.addAll(it) }
                secondSource.articles?.let { allArticlesFromApi.addAll(it) }
                articleNews.postValue(Resource.Success(allArticlesFromApi))
            }
        } catch (e: Exception) {
            articleNews.postValue(Resource.Error(msg = e.localizedMessage))
        }
    }

    fun articleNews(): LiveData<Resource<Article>> = articleNews

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun getSavedArticles() = repository.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun deleteAllArticles() = viewModelScope.launch {
        repository.deleteAllArticle()
    }

    fun isFavourite(url: String) = repository.isFavorite(url)
}
