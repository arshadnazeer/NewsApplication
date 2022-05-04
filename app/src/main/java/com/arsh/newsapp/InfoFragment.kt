package com.arsh.newsapp

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.arsh.newsapp.databinding.FragmentInfoBinding
import com.arsh.newsapp.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        val args : InfoFragmentArgs by navArgs()

        val article = args.selectedArticle

        binding.wvNews.apply {
            webViewClient = WebViewClient()
            if (article.url!=null){
                loadUrl(article.url)
            }
        }

        binding.fabSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }

    }
}