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
import java.util.*
import kotlin.collections.ArrayList

class NewsViewModel(private val newsRepository: NewsRepository , private val favRepo: FavRepo) : ViewModel() {

    private var homeNews: MutableLiveData<Resource<Article>> = MutableLiveData()
    private lateinit var responseList: MutableList<Article>


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


    fun searcQuery (newText: String? , responseList: MutableList<Article> ) : MutableList<Article> {
        val responses: MutableList<Article> = ArrayList()
        for (response in responseList ) {
            /*
            Useful constant for the root locale. The root locale is the locale whose language, country, and variant are empty ("") strings.
            This is regarded as the base locale of all locales, and is used as the language/country neutral locale for the locale sensitive operations.
             */
            val name: String? = response.title?.toLowerCase(Locale.ROOT)
            if (newText?.toLowerCase(Locale.ROOT)?.let { name?.contains(it) }!!)
                responses.add(response)
        }
        return  responses

    }


}
