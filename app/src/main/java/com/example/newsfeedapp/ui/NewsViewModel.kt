package com.example.newsfeedapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    init {
        getHomeNews()
    }

    private fun getHomeNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNewsSources()
        }
    }

    fun getNews() = newsRepository.sourcesList as LiveData<Resource<Article>>

}
