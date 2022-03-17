package com.xj.demo.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class MyProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "cn.xj.provider"
        const val USER_TABLE_NAME = "user"
        const val USER_CODE = 2
    }

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        matcher.addURI(AUTHORITY, USER_TABLE_NAME, USER_CODE)
    }

    var dbHelper: DBHelper? = null
    var db: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        dbHelper = context?.let { DBHelper(it) }
        db = dbHelper?.writableDatabase

        db?.execSQL("delete from user")
        db?.execSQL("insert into user values(1,'Carson');")
        db?.execSQL("insert into user values(2,'Kobe');")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val tableName = getTableName(uri).orEmpty()

        return db?.query(
            tableName,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val tableName = getTableName(uri).orEmpty()
        db?.insert(tableName, null, values)
        //通知外界更新
        context?.contentResolver?.notifyChange(uri, null)

        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    private fun getTableName(uri: Uri): String? {
        return when (matcher.match(uri)) {
            USER_CODE -> USER_TABLE_NAME
            else -> {
                null
            }
        }
    }

}

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "user"
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $DB_NAME (id INTEGER PRIMARY KEY , user_name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DB_NAME")
        onCreate(db)
    }

}