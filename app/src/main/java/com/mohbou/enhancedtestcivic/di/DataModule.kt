package com.mohbou.enhancedtestcivic.di

import android.annotation.SuppressLint
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.mohbou.enhancedtestcivic.common.IntentFactory
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.repository.DBRepository
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.data.network.Mapper
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.domain.MyJson
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.worker.PopulateDatabaseWorker
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

@Module(includes=[AppModule::class])
class DataModule {

    @ApplicationScope
    @Provides
    fun provideQuestionRepository(networkRepository: NetworkRepository,dbRepository: DBRepository): QuestionRepository {
        return QuestionRepository(networkRepository,dbRepository)
    }

    @ApplicationScope
    @Provides
    fun provideNetworkRepository(inputStream: InputStream,gson:Gson): NetworkRepository {
        return NetworkRepository(inputStream,gson)
    }

    @ApplicationScope
    @Provides
    fun provideInputStream(context: Context): InputStream {
        return context.resources.openRawResource(context.resources.getIdentifier("test_civic_question", "raw", context.packageName))
    }

    @ApplicationScope
    @Provides
    fun provideIntentFactory(application: Context): IntentFactory {
        return IntentFactory(application)
    }

    @ApplicationScope
    @Provides
    fun provideGson():Gson {
        return Gson()
    }

    @ApplicationScope
    @Provides
    fun provideDatabase(context: Context):CivicTestDatabase {
        return CivicTestDatabase.getInstance(context)
    }

    @ApplicationScope
    @Provides
    fun provideDBRepository(civicTestDatabase: CivicTestDatabase):DBRepository {
        return DBRepository(civicTestDatabase)
    }

}