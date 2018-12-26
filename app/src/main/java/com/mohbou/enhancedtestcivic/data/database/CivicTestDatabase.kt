package com.mohbou.enhancedtestcivic.data.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.worker.PopulateDatabaseWorker

@Database(entities = [QuestionEntity::class, AnswerEntity::class],version = 1,exportSchema = false)
abstract class CivicTestDatabase :RoomDatabase() {
abstract fun getQuestionDao(): QuestionDao

    companion object {
        @Volatile
        private var instance:CivicTestDatabase? = null

        fun getInstance(context: Context): CivicTestDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {instance = it}
            }
        }

        private fun buildDatabase(context: Context): CivicTestDatabase {
            return Room.databaseBuilder(context,CivicTestDatabase::class.java,"civictest")
                .addCallback(object :RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<PopulateDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                    }
                }).build()
        }


    }

}