package com.example.newsfeedapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var articleNews = MutableLiveData<Resource<Article>>()

    init {
        getHomeNews()
    }

    private fun getHomeNews() {
        articleNews.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsRepository.getNewsSources()
            articleNews.postValue(Resource.Success(result))
            if(result.isNullOrEmpty()){
                articleNews.postValue(Resource.Error(msg="No data saved "))
            }
        }
    }

    fun getNews() = articleNews as LiveData<Resource<Article>>

}
