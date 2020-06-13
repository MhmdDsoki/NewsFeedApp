package com.example.newsfeedapp.data

import androidx.lifecycle.LiveData
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.sources.favouriteLocalData.FavouriteNewsDao
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Test

class FavRepoTest{

    @Test
    fun insert_isInvoked (){
       runBlocking {
           var isInvoked = false
           val y = object : FavouriteNewsDao {
               override suspend fun insert(article: Article): Long {
                   isInvoked = true
                    return super.insert(article)
               }
           }
           val article = Article("", "", "", "", "", "")
           val favRepo = FavRepo(y)
           favRepo.insert(article)
           assertThat(isInvoked, Is.`is`(true))
       }
    }


    @Test
    fun getAllArticles_isInvoked (){
        var isInvoked =false
        val y = object : FavouriteNewsDao{
            override fun getAllArticles(): LiveData<List<Article>>? {
                isInvoked=true
                return super.getAllArticles()
            }
        }
        val favRepo =FavRepo(y)
        favRepo.getAllArticles()
        assertThat(isInvoked, Is.`is`(true))
    }

    @Test
    fun delete_isInvoked (){
        runBlocking {
            var isInvoked = false
            val y = object : FavouriteNewsDao {
                override suspend fun deleteArticle(article: Article) {
                    isInvoked = true

                }
            }
            val article = Article("", "", "", "", "", "")
            val favRepo = FavRepo(y)
            favRepo.deleteArticle(article)
            assertThat(isInvoked, Is.`is`(true))
        }
    }
    @Test
    fun deleteAllArticles_isInvoked (){
        runBlocking {
            var isInvoked = false
            val y = object : FavouriteNewsDao {
                override suspend fun deleteAllArticle(){
                    isInvoked = true
                }
            }
            val favRepo = FavRepo(y)
            favRepo.deleteAllArticle()
            assertThat(isInvoked, Is.`is`(true))
        }
    }

    @Test
    fun isFav_isInovked(){
        var isInvoked = false
        val y = object : FavouriteNewsDao {
            override fun isFavorite(articleUrl: String): Int {
                isInvoked=true
                return super.isFavorite(articleUrl)
            }
        }
        val favRepo = FavRepo(y)
        favRepo.isFavorite("")
        assertThat(isInvoked, Is.`is`(true))
    }

}