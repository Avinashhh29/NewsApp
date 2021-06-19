package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
     suspend fun getBreakingNews(countryCode:String, pageNumber:Int)=
         RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

}