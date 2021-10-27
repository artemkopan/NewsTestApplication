package io.test.app.data.repository

import io.test.app.data.database.NewsDao
import io.test.app.data.mapper.toEntity
import io.test.app.data.mapper.toModel
import io.test.app.data.network.service.NewsApiService
import io.test.app.domain.repository.NewsRepository
import io.test.app.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

    override fun getHeadlinesFlow(): Flow<List<Article>> = flow {
        val localNews = newsDao.getArticles().map { it.toModel() }
        emit(localNews)
        val remoteNews = newsApiService.getHeadlines().articles.orEmpty().map { it.toEntity() }
        newsDao.deleteAll()
        newsDao.insert(remoteNews)
        emit(remoteNews.map { it.toModel() })
    }
}
