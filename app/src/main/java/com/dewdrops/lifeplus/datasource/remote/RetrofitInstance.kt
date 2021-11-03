package com.dewdrops.lifeplus.datasource.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        val api: SearchApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.tvmaze.com/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchApi::class.java)
        }

    }

}