package com.dewdrops.lifeplus.datasource.remote

import com.dewdrops.lifeplus.datasource.model.Show
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("shows")
    fun getSearchResult(
        @Query("q") query: String
    ): Call<List<Show>>

}


// http://api.tvmaze.com/singlesearch/shows?q=abc