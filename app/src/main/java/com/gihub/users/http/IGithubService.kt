package com.gihub.users.http

import com.gihub.users.data.model.Follower
import com.gihub.users.data.model.SearchResultResponse
import com.gihub.users.data.model.User
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGithubService {

    @GET("search/users")
    fun searchUser(@Query("q") name: String,
                    @Query("page")  pageNumber: String): Single<Response<SearchResultResponse>>

    @GET("users/{login_id}")
    fun getUserProfile(@Path("login_id") loginId: String): Single<Response<User>>

    @GET("users/{userId}/followers")
    fun getFollowers(@Path("userId") userId: String):  Single<Response<List<Follower>>>

    @GET("users/{userId}/following")
    fun getFollowings(@Path("userId") userId: String):  Single<Response<List<Follower>>>
}