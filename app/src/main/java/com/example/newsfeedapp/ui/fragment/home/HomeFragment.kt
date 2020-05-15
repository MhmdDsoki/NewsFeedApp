package com.example.newsfeedapp.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.common.gone
import com.example.newsfeedapp.common.show
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.MainActivity
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.Interaction {

    private val viewModel by lazy { (activity as MainActivity).viewModel }

    private val newsAdapter by lazy { NewsAdapter(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeToNewsLiveData()
    }

    private fun observeToNewsLiveData() {
        viewModel.articleNews().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    ProgressBar.gone()
                    newsAdapter.differ.submitList(it.data)
                    swipeReferesh.isRefreshing = false
                }
                is Resource.Error -> {
                    ProgressBar.gone()
                    swipeReferesh.isRefreshing = false
                }
                is Resource.Loading -> {
                    ProgressBar.show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        swipeReferesh.apply {
            setOnRefreshListener {
                observeToNewsLiveData()
            }
        }
        articlesNewsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        val action = HomeFragmentDirections.actionNavExploreToDetailsFragment(item)
        findNavController().navigate(action)
    }
}
