package  com.example.newsfeedapp.di


import com.example.newsfeedapp.data.NewsRepository
import com.example.newsfeedapp.data.FavRepo
import org.koin.dsl.module

val repo = module {

    // Provide NewsRepository
    single { NewsRepository(get(), get()) }

    single { FavRepo(get()) }
}
