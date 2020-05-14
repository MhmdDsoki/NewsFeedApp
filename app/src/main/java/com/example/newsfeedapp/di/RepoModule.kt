package  com.example.newsfeedapp.di



import com.example.newsfeedapp.data.NewsRepository
import org.koin.dsl.module

val repo = module {

       // Provide NewsRepository
    single { NewsRepository (get(),get()) }


}
