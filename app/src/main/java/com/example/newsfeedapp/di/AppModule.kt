package  com.example.newsfeedapp.di

import androidx.room.Room
import com.example.newsfeedapp.data.sources.localData.NewsDataBase

import org.koin.dsl.module


val appModule = module{


    // Provide NewsDatabase
    single{ Room.databaseBuilder(get(), NewsDataBase::class.java, "NEWS_DATABASE_NAME").fallbackToDestructiveMigration().build() }

    // Provide NewsDao
    single{ get<NewsDataBase>().getNewsDao() }



}