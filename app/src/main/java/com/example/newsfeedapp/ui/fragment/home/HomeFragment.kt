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
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.common.gone
import com.example.newsfeedapp.common.show
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.MainActivity
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList


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
        viewModel.articleNews().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        ProgressBar.gone()
                        newsAdapter.differ.submitList(it.data)
                        swipeReferesh.isRefreshing = false
                        it.data?.let { articles -> responseList.addAll(articles) } // add the call from api to list in memory to search
                    }
                }
                is Resource.Error -> {
                    ProgressBar.gone()
                    swipeReferesh.isRefreshing = false
                }

                is Resource.Loading -> ProgressBar.show()
            }
        })
    }

    private fun setupRecyclerView() {
        swipeReferesh.apply {
            setOnRefreshListener {
                responseList.clear()
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
        val responses: MutableList<Article> = ArrayList()
        for (response in responseList) {
            /*
            Useful constant for the root locale. The root locale is the locale whose language, country, and variant are empty ("") strings.
            This is regarded as the base locale of all locales, and is used as the language/country neutral locale for the locale sensitive operations.
             */
            val name: String? = response.title?.toLowerCase(Locale.ROOT)
            if (newText?.toLowerCase(Locale.ROOT)?.let { name?.contains(it) }!!)
                responses.add(response)
        }
        newsAdapter.differ.submitList(responses)
        return true
    }
}
