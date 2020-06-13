package  com.example.newsfeedapp.di


import com.example.newsfeedapp.ui.NewsViewModel
import com.example.newsfeedapp.ui.fragment.wish_list.FavouriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { FavouriteViewModel (get())}

}

