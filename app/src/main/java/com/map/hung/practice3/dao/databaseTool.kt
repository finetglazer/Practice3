package com.map.hung.practice3.dao

//import DbHelper
import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.map.hung.practice3.R

fun connectDatabase(context: Context): SQLiteDatabase {
    val dbPath = context.getExternalFilesDir(null)?.absolutePath + "/manage.db"
    return SQLiteDatabase.openDatabase(
        dbPath,
        null,
        SQLiteDatabase.OPEN_READWRITE
    )
}

fun delete(db: SQLiteDatabase, tableName: String) {
    db.execSQL("DROP TABLE IF EXISTS $tableName")
}
class MyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val dbHelper = DbHelper(this)
        val db = dbHelper.writableDatabase  // This will create or open the database

        connectDatabase(this);
//        delete(db, "tbUser")
//        addNewTable(this)
//        deleteNewTable(this)
    }

}