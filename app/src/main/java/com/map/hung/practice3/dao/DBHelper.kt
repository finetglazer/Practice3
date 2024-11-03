package com.map.hung.practice3.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(
    context,
    context.getExternalFilesDir(null)?.absolutePath + "/manage.db", // Use the path for 'keke.db'
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase) {
        // This will be called the first time the database is created
        db.execSQL("CREATE TABLE IF NOT EXISTS example (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade logic here
    }

    companion object {
        fun getDatabase(context: Context): SQLiteDatabase {
            // Use DbHelper to manage database creation and opening
            val dbHelper = DbHelper(context)
            return dbHelper.writableDatabase  // This will create or open the database
        }
    }
}
