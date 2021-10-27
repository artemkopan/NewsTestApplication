package io.test.app.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.test.app.R
import io.test.app.model.Article

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_article,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.itemView.run {
            val item = getItem(position)
            findViewById<TextView>(R.id.author).text = item.author
            findViewById<TextView>(R.id.title).text = item.title
            findViewById<TextView>(R.id.description).text = item.description
        }
    }

    class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private val diff = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}