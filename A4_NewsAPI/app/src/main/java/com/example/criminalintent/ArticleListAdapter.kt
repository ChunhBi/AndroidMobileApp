package com.example.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemArticleBinding

class ArticleHolder (
    val binding: ListItemArticleBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(article: Article, onArticleClicked: (articleId: Article) -> Unit) {
        binding.articleTitle.text = article.title
        binding.articleDate.text = article.publishedAt
        binding.articleAuthor.text = article.author
        binding.root.setOnClickListener {
            onArticleClicked(article)
        }
    }
}
class ArticleListAdapter (
    private val articles: List<Article>,
    private val onArticleClicked: (article: Article) -> Unit
) : RecyclerView.Adapter<ArticleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemArticleBinding.inflate(inflater, parent, false)
        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, onArticleClicked)
    }

    override fun getItemCount() = articles.size
}