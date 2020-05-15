package com.example.newsfeedapp.ui.fragment.details

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsfeedapp.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment(R.layout.fragment_web_view) {
    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.webViewUrl)
        }
    }
}
