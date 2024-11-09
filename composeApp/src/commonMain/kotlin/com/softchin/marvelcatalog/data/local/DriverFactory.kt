package com.softchin.marvelcatalog.data.local

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDataBaseDriver(): SqlDriver
}
