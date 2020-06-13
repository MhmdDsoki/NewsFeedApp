package com.example.newsfeedapp.ui.fragment.wish_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.data.FavRepo
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel (private val favRepo: FavRepo) : ViewModel() {




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

}