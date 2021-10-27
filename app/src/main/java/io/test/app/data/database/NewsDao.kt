package io.test.app.data.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.CancellationSignal
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class NewsDao(
    private val dbHelper: NewsDatabaseHelper
) {

    suspend fun getArticles() = suspendCancellableCoroutine<List<ArticleEntity>> { continuation ->
        val cancellationSignal = CancellationSignal()
        continuation.invokeOnCancellation { cancellationSignal.cancel() }
        val cursor = dbHelper.readableDatabase?.rawQuery(
            "select * from ${dbHelper.tableName}",
            emptyArray(),
            cancellationSignal
        )
        if (cursor == null) {
            continuation.resume(emptyList())
            return@suspendCancellableCoroutine
        }
        val items = mutableListOf<ArticleEntity>()
        cursor.use {
            it.run {
                if (moveToFirst()) {
                    do {
                        val element = ArticleEntity(
                            title = getString(getColumnIndexOrThrow(dbHelper.columnTitle)),
                            description = getString(getColumnIndexOrThrow(dbHelper.columnDescription)),
                            author = getString(getColumnIndexOrThrow(dbHelper.columnAuthor))
                        )
                        items.add(element)
                    } while (moveToNext())
                }
            }
        }
        continuation.resume(items)
    }

    fun deleteAll() {
        dbHelper.writableDatabase.execSQL("delete from ${dbHelper.tableName}")
    }

    fun insert(articles: List<ArticleEntity>) {
        val db = dbHelper.writableDatabase

        articles.forEach {
            val contentValues = ContentValues()
            contentValues.put(dbHelper.columnAuthor, it.author)
            contentValues.put(dbHelper.columnTitle, it.title)
            contentValues.put(dbHelper.columnDescription, it.description)

            db.insertWithOnConflict(
                dbHelper.tableName,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        }
    }
}