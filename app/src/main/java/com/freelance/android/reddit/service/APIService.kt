package com.freelance.android.reddit.service

import com.freelance.android.reddit.model.RedditModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("top.json")
    fun fetchRedditNews(@Query("limit") limit: Int): Observable<RedditModel>

    companion object {
        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.reddit.com/")
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}
