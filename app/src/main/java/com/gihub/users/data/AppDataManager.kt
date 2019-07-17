package com.gihub.users.data

import android.content.Context
import com.gihub.users.data.model.Follower
import com.gihub.users.data.model.User
import com.gihub.users.data.remote.RemoteDataManager
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class AppDataManager
@Inject constructor(
    private var context: Context,
    private var remoteDataManager: RemoteDataManager
) : IAppDataSource {
    override fun getUserProfile(userId: String) = remoteDataManager.getUserProfile(userId)

    override fun searchUsers(query: String, pageNumber: String) =
        remoteDataManager.searchUsers(query, pageNumber)

    override fun getFollowers(userId: String) = remoteDataManager.getFollowers(userId)

    override fun getFollowings(userId: String) = remoteDataManager.getFollowings(userId)
}