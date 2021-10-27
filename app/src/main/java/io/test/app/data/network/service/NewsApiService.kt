package io.test.app.data.network.service

import io.test.app.data.network.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): ArticleResponse

    private companion object {
        const val API_KEY = "9b058821e07a43dd9797f6350a0e3e48"
    }
}