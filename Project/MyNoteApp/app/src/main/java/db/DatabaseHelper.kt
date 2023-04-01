package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE =
            "CREATE TABLE ${DatabaseConstract.NoteColumns.TABLE_NAME}" +
                    " (${DatabaseConstract.NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseConstract.NoteColumns.TITLE} TEXT NOT NULL," +
                    " ${DatabaseConstract.NoteColumns.DESCRIPTION} TEXT NOT NULL," +
                    " ${DatabaseConstract.NoteColumns.DATE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseConstract.NoteColumns.TABLE_NAME}")
        onCreate(db)
    }
}