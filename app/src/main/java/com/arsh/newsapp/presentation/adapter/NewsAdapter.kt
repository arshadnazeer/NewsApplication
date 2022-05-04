package com.arsh.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.databinding.NewsListItemBinding
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val itemCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, itemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(
        private val newsListItemBinding: NewsListItemBinding
    ) : RecyclerView.ViewHolder(newsListItemBinding.root) {
        fun bind(article: Article) {
            newsListItemBinding.tvTitle.text = article.title
            newsListItemBinding.tvDescription.text = article.description
            newsListItemBinding.tvSource.text = article.source?.name
            newsListItemBinding.tvPublishedAt.text = article.publishedAt
            newsListItemBinding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }


            Glide.with(newsListItemBinding.ivArticleImage.context)
                .load(article.urlToImage)
                .into(newsListItemBinding.ivArticleImage)



        }
    }


    private var onItemClickListener : ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }
}