package com.gihub.users.data.remote

import com.gihub.users.data.model.Follower
import com.gihub.users.data.model.SearchResultResponse
import com.gihub.users.data.model.User
import io.reactivex.Single
import retrofit2.Response

interface IRemoteSource {
    fun searchUsers(query: String, pageNumber: String)
            : Single<Response<SearchResultResponse>>

    fun getUserProfile(userId: String)
            : Single<Response<User>>

    fun getFollowers(userId: String)
            : Single<Response<List<Follower>>>

    fun getFollowings(userId: String)
            : Single<Response<List<Follower>>>
}