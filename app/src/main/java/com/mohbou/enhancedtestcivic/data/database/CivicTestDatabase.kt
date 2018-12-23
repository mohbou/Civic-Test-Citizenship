package com.mohbou.enhancedtestcivic.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity

@Database(entities = [QuestionEntity::class, AnswerEntity::class],version = 1,exportSchema = false)
abstract class CivicTestDatabase :RoomDatabase() {
abstract fun getQuestionDao(): QuestionDao
}