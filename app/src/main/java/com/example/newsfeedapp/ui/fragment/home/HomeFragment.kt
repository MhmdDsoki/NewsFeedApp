package com.example.newsfeedapp.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.common.gone
import com.example.newsfeedapp.common.show
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.MainActivity
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.Interaction {

     val  viewModel by lazy {(activity as MainActivity).viewModel}


    private  val newsAdapter by lazy { NewsAdapter (this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchArticlesNewsFromApi("the-next-web")
        setupRecyclerView()
        observeToNewsLiveData()






    }

    private fun observeToNewsLiveData() {
        viewModel.articleNews().observe(viewLifecycleOwner, Observer {
        when(it){
            is  Resource.Success ->{
                ProgressBar.gone()
                newsAdapter.differ.submitList(it.data)
            }
            is Resource.Error -> {
                newsAdapter.differ.submitList(it.data)
                ProgressBar.gone()
            }
            is Resource.Loading ->{
                ProgressBar.show()
            }
        }
    })

    }

    private fun setupRecyclerView() {
        articlesNewsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {

    }
}
