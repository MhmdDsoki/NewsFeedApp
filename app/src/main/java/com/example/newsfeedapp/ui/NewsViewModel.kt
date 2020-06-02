package com.example.newsfeedapp.ui

import androidx.lifecycle.*
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.FavRepo
import com.example.newsfeedapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class NewsViewModel(private val newsRepository: NewsRepository, private val favRepo: FavRepo) : ViewModel() {

    init {
        getHomeNews()
    }

    private fun getHomeNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNewsSources()
        }
    }


    fun getNews(): MutableLiveData<Resource<Article>> = newsRepository.sourcesList

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        favRepo.insert(article)
    }

    fun getSavedArticles() = favRepo.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        favRepo.deleteArticle(article)
    }

    fun deleteAllArticles() = viewModelScope.launch(Dispatchers.IO) {
        favRepo.deleteAllArticle()
    }

    fun isFavourite(url: String) = favRepo.isFavorite(url)


    fun searchQuery(newText: String?, responseList: MutableList<Article>): MutableList<Article> {
        val responses: MutableList<Article> = ArrayList()
        for (response in responseList) {
            /*
            Useful constant for the root locale. The root locale is the locale whose language, country, and variant are empty ("") strings.
            This is regarded as the base locale of all locales, and is used as the language/country neutral locale for the locale sensitive operations.
             */
            val name: String? = response.title?.toLowerCase(Locale.ROOT)
            if (newText?.toLowerCase(Locale.ROOT)?.let { name?.contains(it) }!!)
                responses.add(response)
        }
        return responses
    }
}
