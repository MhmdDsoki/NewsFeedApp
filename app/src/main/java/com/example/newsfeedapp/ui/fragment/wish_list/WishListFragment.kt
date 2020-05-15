package com.example.newsfeedapp.ui.fragment.wish_list

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.showDialog
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.MainActivity
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_wish_list.*
import java.util.*
import kotlin.collections.ArrayList


class WishListFragment : Fragment(R.layout.fragment_wish_list), NewsAdapter.Interaction,
    SearchView.OnQueryTextListener {

    private val viewModel by lazy { (activity as MainActivity).viewModel }
    private val newsAdapter by lazy { NewsAdapter(this) }
    private lateinit var favList: MutableList<Article>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favList = mutableListOf()
        setHasOptionsMenu(true)
        setupRecyclerView()
        observeToFavLiveData()


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(favNewsRecycler)
        }
    }

    private fun observeToFavLiveData() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { articles ->
            if (articles != null) {
                newsAdapter.differ.submitList(articles)
                favList.addAll(articles)
            }
        })
    }

    private fun setupRecyclerView() {
        favNewsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        val action = WishListFragmentDirections.actionNavWishListToDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_deleteAll -> {

                if (favList.isNotEmpty())
                    showDialog(getString(R.string.deleteAll), " Yes , Delete "
                        , DialogInterface.OnClickListener { dialog, which ->
                            viewModel.deleteAllArticles()
                        }, "No", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }, true
                    )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav_menu, menu)
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
        for (response in favList) {
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
