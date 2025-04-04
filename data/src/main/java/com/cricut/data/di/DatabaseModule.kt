package com.cricut.data.di

import android.content.Context
import androidx.room.Room
import com.cricut.data.dao.QuestionsDao
import com.cricut.data.database.QuestionsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): QuestionsDb {
        return Room.databaseBuilder(context, QuestionsDb::class.java, "questions.db")
            .createFromAsset("questions.db")
            .build()
    }

    @Provides
    fun provideQuestionDao(appDatabase: QuestionsDb): QuestionsDao {
        return appDatabase.questionDao()
    }
}