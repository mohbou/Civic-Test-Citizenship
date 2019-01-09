package com.mohbou.enhancedtestcivic.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import com.mohbou.enhancedtestcivic.domain.Question
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
    fun addAllAnswers(answers:List<AnswerEntity>):List<Long>

    @Update
    fun updateQuestion(questionEntity: QuestionEntity)

    @Transaction
    @Query("SELECT * FROM question_table")
    fun getAllQuestions():Single<List<QuestionWithAnswersEntity>>?

    @Query("SELECT * FROM question_table")
    fun getQuestions(): LiveData<List<Question>>

    @Query("SELECT * FROM question_table")
    fun getQuestionsDomain(): LiveData<List<Question>>

    @Transaction
    @Query("SELECT * FROM question_table WHERE id = :questionId")
    fun getQuestionById(questionId:String?):QuestionWithAnswersEntity

    @Query("SELECT COUNT(id) FROM question_table")
    fun getNumberOfRows(): Single<Int>?

    @Query("SELECT * FROM answer")
    fun getAllAnswers(): LiveData<List<AnswerEntity>>?

    @Query("SELECT COUNT(id) FROM answer")
    fun getAnswerCount(): Int?

    @Query("SELECT * FROM question_table WHERE review= 1")
    fun getAllQuestionsToReview():LiveData<List<Question>>

    @Query("UPDATE question_table SET review = :review WHERE id = :questionId")
    fun updateQuestionReview(questionId: String?, review: Int)
}