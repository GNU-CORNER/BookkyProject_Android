package com.example.bookkyandroid.config

class DBController {
    companion object{
        @Volatile private var instance: DBController? = null

        @JvmStatic fun getInstance(): DBController =
            instance ?: synchronized(this){
                instance ?: DBController().also {
                    instance = it
                }
            }
    }
    var dbHelper : FeedReaderContract.FeedReaderDbHelper? = null
    fun setter(dbHelper: FeedReaderContract.FeedReaderDbHelper){
        this.dbHelper = dbHelper
    }
    fun getter(): FeedReaderContract.FeedReaderDbHelper? {
        return this.dbHelper
    }
}