package io.test.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.test.app.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class NewsViewModel(newsRepository: NewsRepository) : ViewModel() {

    val errorChannel = Channel<Throwable>(1)

    val newsItems = newsRepository.getHeadlinesFlow()
        .flowOn(Dispatchers.IO)
        .catch { errorChannel.send(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}