package com.example.newsapp.apiservice

import retrofit2.http.GET

interface ApiService {
    @GET(value = "/v2/top-headlines")
    suspend fun getTopNews()
}