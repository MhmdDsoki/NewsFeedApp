package  com.example.newsfeedapp.di

import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.newsfeedapp.R
import com.example.newsfeedapp.data.sources.localData.NewsDataBase
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module


val appModule = module{


    // Provide NewsDatabase
    single{ Room.databaseBuilder(get(), NewsDataBase::class.java, "NEWS_DATABASE_NAME").fallbackToDestructiveMigration().allowMainThreadQueries().build() }

    // Provide NewsDao
    single{ get<NewsDataBase>().getNewsDao() }



    single {
        RequestOptions
            .placeholderOf(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    single {
        Glide.with(androidContext())
            .setDefaultRequestOptions(get())

    }



}