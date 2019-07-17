package com.gihub.users.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Follower(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String,
    @SerializedName("gists_url") val gistsUrl: String,
    @SerializedName("starred_url") val starredUrl: String,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String,
    @SerializedName("organizations_url") val organizationsUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url") val eventsUrl: String,
    @SerializedName("received_events_url") val receivedEventsUrl: String,
    @SerializedName("type") val type: String,
    @SerializedName("site_admin") val siteAdmin: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(login)
        writeInt(id)
        writeString(nodeId)
        writeString(avatarUrl)
        writeString(gravatarId)
        writeString(url)
        writeString(htmlUrl)
        writeString(followersUrl)
        writeString(followingUrl)
        writeString(gistsUrl)
        writeString(starredUrl)
        writeString(subscriptionsUrl)
        writeString(organizationsUrl)
        writeString(reposUrl)
        writeString(eventsUrl)
        writeString(receivedEventsUrl)
        writeString(type)
        writeInt((if (siteAdmin) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Follower> = object : Parcelable.Creator<Follower> {
            override fun createFromParcel(source: Parcel): Follower = Follower(source)
            override fun newArray(size: Int): Array<Follower?> = arrayOfNulls(size)
        }
    }
}