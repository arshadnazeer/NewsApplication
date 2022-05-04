package com.arsh.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsh.newsapp.databinding.ActivityMainBinding
import com.arsh.newsapp.databinding.FragmentNewsBinding
import com.arsh.newsapp.presentation.adapter.NewsAdapter
import com.arsh.newsapp.presentation.viewmodel.NewsViewModel
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        viewNews()
        setSearch()

        newsAdapter.setOnItemClickListener {
            if (it.equals(null)){
                Toast.makeText(activity,"Article not available",Toast.LENGTH_SHORT).show()
            }
            else{
                val bundle = Bundle().apply {
                    putSerializable("selected_article",it)
                }
                findNavController().navigate(
                    R.id.action_newsFragment_to_infoFragment,
                    bundle

                )
            }
        }
    }

    private fun viewNews() {
        viewModel.getNewsHeadLines(country,page)
        viewModel.newHeadLines.observe(viewLifecycleOwner,{ response ->

            when(response){
                is com.arsh.newsapp.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }

                is com.arsh.newsapp.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.data?.let {
                        Toast.makeText(activity,"An error occurred:  $it",Toast.LENGTH_SHORT).show()
                    }
                }

                is com.arsh.newsapp.data.util.Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
    }


    fun setSearch(){
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    viewModel.searchNews(country,page,p0.toString())
                    searchNews()
                }
                return false

            }

        })
    }

    private fun searchNews(){
        viewModel.searchNewsHeadlines.observe(viewLifecycleOwner,{ response ->
            when(response){
                is com.arsh.newsapp.data.util.Resource.Error ->{
                    hideProgressBar()
                    response.data?.let {
                        Toast.makeText(activity,"Some Error Occured",Toast.LENGTH_SHORT).show()
                    }
                }

                is com.arsh.newsapp.data.util.Resource.Loading ->{
                    showProgressBar()
                }

                is com.arsh.newsapp.data.util.Resource.Success -> {
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
}