package com.arsh.newsapp.data.db

import androidx.room.*
import com.arsh.newsapp.data.model.Article
import java.util.concurrent.Flow

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM ARTICLES")
    fun getAllArticles() : kotlinx.coroutines.flow.Flow<List<Article>>

    @Delete
    suspend fun deleteSavedArticle(article: Article)
}