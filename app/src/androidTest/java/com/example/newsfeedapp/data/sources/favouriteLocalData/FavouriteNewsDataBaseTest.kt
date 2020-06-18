package com.example.newsfeedapp.data.sources.favouriteLocalData

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsfeedapp.data.model.Article
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavouriteNewsDataBaseTest{
    private lateinit var dataDao: FavouriteNewsDao
    private lateinit var db: FavouriteNewsDataBase
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, FavouriteNewsDataBase::class.java)
            .build()
        dataDao = db.getFavouriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFavourite_withSameUrl_returnOne() {
            val article = Article(author = "one", url = "b", publishedAt = "b", description = "c", urlToImage = "d", title = "e")

        runBlocking {
            dataDao.insert(article)
        }
        val isFavourite =dataDao.isFavorite("b")
        assertThat(isFavourite, Is.`is`(1))

    }
    @Test
    @Throws(Exception::class)
    fun insertFavourite_withDifferentUrl_returnZero() {
        val article = Article(author = "one", url = "b", publishedAt = "b", description = "c", urlToImage = "d", title = "e")

        runBlocking {
            dataDao.insert(article)
        }
        val isFavourite =dataDao.isFavorite("a")
        assertThat(isFavourite, Is.`is`(0))
    }

}