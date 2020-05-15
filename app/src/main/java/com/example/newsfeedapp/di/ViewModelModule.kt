package  com.example.newsfeedapp.di


import com.example.newsfeedapp.ui.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { NewsViewModel(get()) }

}

