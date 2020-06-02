package  com.example.newsfeedapp.di

import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.newsfeedapp.R
import com.example.newsfeedapp.data.sources.favouriteLocalData.FavouriteNewsDataBase
import com.example.newsfeedapp.data.sources.homeCahedData.HomeNewsDataBase
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module


val roomModule = module {
        single {
            Room.databaseBuilder(get(), FavouriteNewsDataBase::class.java, "FAV_DATABASE_NAME")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        single { get<FavouriteNewsDataBase>().getFavouriteDao() }    //  FavouriteNewsDataBase().getFavouriteDao()


        single {
            Room.databaseBuilder(get(), HomeNewsDataBase::class.java, "NEWS_DATABASE_NAME")
                .fallbackToDestructiveMigration()
                .build()
        }

        single { get<HomeNewsDataBase>().getHomeNewsDao() }
}

val glideModule = module {
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