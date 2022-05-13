package com.example.myapp01

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface SearchQuoteInterface {
    @GET(".")
    fun searchquote(@Query("query") querytag: String): Call<QuoteData>
}

