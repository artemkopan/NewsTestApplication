package io.test.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.test.app.R
import io.test.app.dependencies.ServiceLocator
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewsActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            ServiceLocator.getNewsViewModelFactory()
        )[NewsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val adapter = ArticleAdapter()
        findViewById<RecyclerView>(R.id.news_recycler).apply {
            this.adapter = adapter
            addItemDecoration(ArticleItemDecoration(resources))
            addItemDecoration(
                DividerItemDecoration(
                    this@NewsActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        viewModel
            .newsItems
            .onEach { adapter.submitList(it) }
            .launchIn(lifecycle.coroutineScope)

        viewModel
            .errorChannel
            .consumeAsFlow()
            .onEach { Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_LONG).show() }
            .launchIn(lifecycle.coroutineScope)
    }
}