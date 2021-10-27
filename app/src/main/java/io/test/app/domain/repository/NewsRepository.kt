package io.test.app.domain.repository

import io.test.app.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getHeadlinesFlow(): Flow<List<Article>>

}