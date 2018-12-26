package com.mohbou.enhancedtestcivic.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import io.reactivex.Single

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuestion(questionEntity: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAnswer(answerEntity: AnswerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllQuestions(questions:List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllAnswers(answers:List<AnswerEntity>)

    @Transaction
    @Query("SELECT * FROM question")
    fun getAllQuestions():List<QuestionWithAnswersEntity>?

    @Transaction
    @Query("SELECT * FROM question WHERE id = :questionId")
    fun getQuestionById(questionId:String):LiveData<QuestionWithAnswersEntity>

    @Query("SELECT COUNT(id) FROM question")
    fun getNumberOfRows(): Single<Int>?

    @Transaction
    @Query("SELECT * FROM question")
    fun getAllQuestionsBis():Single<List<QuestionWithAnswersEntity>>?
}