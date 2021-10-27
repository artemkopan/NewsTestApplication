package io.test.app.data.mapper

import io.test.app.data.database.ArticleEntity
import io.test.app.data.network.response.ArticleResponse
import io.test.app.model.Article

fun ArticleEntity.toModel() = Article(
    title = title,
    description = description,
    author = author
)

fun ArticleResponse.Item.toEntity() = ArticleEntity(
    title = title.orEmpty(),
    description = description.orEmpty(),
    author = author.orEmpty()
)