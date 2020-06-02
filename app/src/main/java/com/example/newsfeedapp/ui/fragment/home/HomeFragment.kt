package com.example.newsfeedapp.ui.fragment.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.*
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.MainActivity
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.Interaction,
    SearchView.OnQueryTextListener {

    private val viewModel by lazy { (activity as MainActivity).viewModel }
    private val newsAdapter by lazy { NewsAdapter(this) }

    private lateinit var responseList: MutableList<Article>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        responseList = mutableListOf()

        setupRecyclerView()
        observeToNewsLiveData()
    }




    private fun observeToNewsLiveData() {
        viewModel.getNews().observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Error -> {
                    ProgressBar.gone()
                    swipeRefresh.isRefreshing = false
                    newsAdapter.differ.submitList(it.data)
                }
                is Resource.Loading -> ProgressBar.show()
                is Resource.Success -> {
                    if (it.data != null) {
                        ProgressBar.gone()
                        swipeRefresh.isRefreshing = false
                        newsAdapter.differ.submitList(it.data)
                        responseList.addAll(it.data)  // add the call from api to list in memory to search
                    }
                }
            }
        })
    }


    private fun setupRecyclerView() {
        swipeRefresh.apply {
            setOnRefreshListener {
                responseList.clear()
                observeToNewsLiveData()
            }
        }

        newsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        val action = HomeFragmentDirections.actionNavExploreToDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryTextChange(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newsAdapter.differ.submitList(viewModel.searchQuery(newText, responseList))
        return true
    }

}
