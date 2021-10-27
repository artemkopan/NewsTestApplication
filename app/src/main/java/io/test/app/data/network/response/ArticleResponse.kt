package io.test.app.data.network.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("articles")
    val articles: List<Item>? = null,

    ) {

    data class Item(

        @field:SerializedName("author")
        val author: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("title")
        val title: String? = null
    )
}
