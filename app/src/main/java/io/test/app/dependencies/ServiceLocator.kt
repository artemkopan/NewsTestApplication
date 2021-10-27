package io.test.app.dependencies

import android.app.Application
import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.test.app.data.database.NewsDao
import io.test.app.data.database.NewsDatabaseHelper
import io.test.app.data.network.service.NewsApiService
import io.test.app.data.repository.NewsRepositoryImpl
import io.test.app.domain.repository.NewsRepository
import io.test.app.presentation.NewsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    lateinit var application: Application

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val newsDao by lazy { NewsDao(NewsDatabaseHelper(application)) }
    private val newsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    fun init(application: Application) {
        this.application = application
    }

    fun getNewsRepository(): NewsRepository = NewsRepositoryImpl(newsDao, newsApiService)

    fun getNewsViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewsViewModel(getNewsRepository()) as T
            }
        }
    }
}