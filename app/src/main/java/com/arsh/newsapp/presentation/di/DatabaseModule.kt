package com.arsh.newsapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.arsh.newsapp.data.db.ArticleDAO
import com.arsh.newsapp.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(app:Application) : ArticleDatabase{
        return Room.databaseBuilder(app,ArticleDatabase::class.java,"news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsDAO(articleDatabase: ArticleDatabase) : ArticleDAO{
        return articleDatabase.getArticleDAO()
    }

}