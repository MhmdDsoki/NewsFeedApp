package com.example.newsfeedapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository , private val favRepo: FavRepo) : ViewModel() {

    var homeNews: MutableLiveData<Resource<Article>> = MutableLiveData()

    init{
        getHomeNews()
    }

    fun getHomeNews(){
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getArticles()
        }
        homeNews=newsRepository.articlesNews
    }

    fun getNews () : LiveData<Resource<Article>> =homeNews




    fun saveArticle(article: Article) = viewModelScope.launch {
        favRepo.insert(article)
    }

    fun getSavedArticles() = favRepo.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        favRepo.deleteArticle(article)
    }

    fun deleteAllArticles() = viewModelScope.launch {
        favRepo.deleteAllArticle()
    }

    fun isFavourite(url: String) = favRepo.isFavorite(url)


}
