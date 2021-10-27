package io.test.app.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NewsDatabaseHelper(
    context: Context?,
    name: String? = "news.db",
    version: Int = 1
) : SQLiteOpenHelper(context, name, null, version) {

    val tableName = "article"
    val columnAuthor = "author"
    val columnTitle = "title"
    val columnDescription = "description"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $tableName 
            (_id INTEGER PRIMARY KEY AUTOINCREMENT,
            $columnAuthor TEXT,
            $columnDescription TEXT,
            $columnTitle TEXT);
    """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }
}