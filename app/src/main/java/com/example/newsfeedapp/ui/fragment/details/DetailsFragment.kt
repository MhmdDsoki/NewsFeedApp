package com.example.newsfeedapp.ui.fragment.details


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.Util
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.core.KoinComponent
import org.koin.core.get


class DetailsFragment : Fragment(R.layout.fragment_details), KoinComponent {
    private val args: DetailsFragmentArgs by navArgs()
    private val glide: RequestManager = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glide.load(args.article.urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ArticleImage)

        TitleTxt.text = args.article.title
        PublisherNameTxt.text = args.article.author
        PublisherDateTxt.text = Util.dateFormat(args.article.publishedAt)
        descriptionTxt.text = args.article.description

        openWebSite.setOnClickListener {
            val action = args.article.url?.let { url ->
                DetailsFragmentDirections.actionDetailsFragmentToWebViewFragment(url)
            }
            action?.let { action -> findNavController().navigate(action) }
        }
    }
}

