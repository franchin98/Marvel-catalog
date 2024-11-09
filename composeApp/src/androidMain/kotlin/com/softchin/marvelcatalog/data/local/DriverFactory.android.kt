package com.softchin.marvelcatalog.data.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.softchin.marvel.database.Marvel


actual class DriverFactory(private val context: Context) {

    actual fun createDataBaseDriver(): SqlDriver {
        return AndroidSqliteDriver(Marvel.Schema, context, "marvel.db", callback = object : AndroidSqliteDriver.Callback(Marvel.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                db.setForeignKeyConstraintsEnabled(true)
            }
        } )
    }
}