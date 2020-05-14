package com.example.newsfeedapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.launch

class NewsViewModel (private val repository: NewsRepository) : ViewModel() {

    private val articleNews = MutableLiveData<Resource<Article>> ()
   fun fetchArticlesNewsFromApi(sourceName: String) =  viewModelScope.launch {
           articleNews.postValue(Resource.Loading())
       try {
           articleNews.postValue(repository.getArticlesNews(sourceName).articles?.let { Resource.Success(it) })
       }catch (e : Exception){

           articleNews.postValue(Resource.Error(msg = e.localizedMessage))
       }
   }


    fun articleNews () : LiveData<Resource<Article>> = articleNews


}
