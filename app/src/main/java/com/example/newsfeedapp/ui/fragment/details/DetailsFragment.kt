package com.example.newsfeedapp.ui.fragment.details


import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.Util
import com.example.newsfeedapp.common.showMsg
import com.example.newsfeedapp.common.showToast
import com.example.newsfeedapp.ui.MainActivity
import com.github.ivbaranov.mfb.MaterialFavoriteButton.OnFavoriteChangeListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.core.KoinComponent
import org.koin.core.get


class DetailsFragment : Fragment(R.layout.fragment_details), KoinComponent {
    private val args: DetailsFragmentArgs by navArgs()
    private val glide: RequestManager = get()
    private val viewModel by lazy { (activity as MainActivity).viewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glide.load(args.article.urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ArticleImage)

        TitleTxt.text = args.article.title
        PublisherNameTxt.text = args.article.author
        PublisherDateTxt.text = Util.dateFormat(args.article.publishedAt)
        ArticleTimeAgo.text = Util.dateToTimeFormat(args.article.publishedAt)
        descriptionTxt.text = args.article.description

        if (viewModel.isFavourite(args.article.url) == 1) {
            favBtn.isFavorite = true
            setOnFavListner()
        }
        else
            setOnFavListner()


        shareBtn.setOnClickListener {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plan"
                i.putExtra(Intent.EXTRA_SUBJECT, args.article.title)
                val body: String =
                    args.article.title.toString() + "\n" + args.article.url + "\n" + "Share from the News App" + "\n"
                i.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(i, "Share with :"))
            } catch (e: Exception) {
                showToast("Sorry, \nCannot be share")
            }
        }
        openWebSite.setOnClickListener {
            val action = args.article.url?.let { url ->
                DetailsFragmentDirections.actionDetailsFragmentToWebViewFragment(url)
            }
            action?.let { action -> findNavController().navigate(action) }
        }
    }
    private fun setOnFavListner() {
        favBtn.setOnFavoriteChangeListener { buttonView, favorite ->
            if (favorite) {
                viewModel.saveArticle(args.article)
                showMsg("Added")
            } else {
                viewModel.deleteArticle(args.article)
                showMsg("removed")
            }
        }
    }
}

