package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemArticlePreviewBinding
import com.example.myapplication.models.Article


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    
    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root)


    private val differCallBack= object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding= ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article= differ.currentList[position]
        holder.itemView.apply {
            Glide.with(holder.itemView.context).load(article.urlToImage).into(holder.binding.ivArticleImage)
            holder.binding.tvSource.text=article.source.name
            holder.binding.tvTitle.text=article.title
            holder.binding.tvDescription.text=article.description
            holder.binding.tvPublishedAt.text=article.publishedAt
            setOnClickListener {
                OnItemClickListener?.let { it(article) }
            }
        }
    }

    private var OnItemClickListener:((Article)-> Unit)?=null

    fun setOnItemClickListener(listener:((Article)-> Unit)){
        OnItemClickListener=listener
    }


}