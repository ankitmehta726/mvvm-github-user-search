package com.gihub.users.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created By ankitmehta726
 */

data class SearchResultResponse(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val userList: List<SearchedUser>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        1 == source.readInt(),
        source.createTypedArrayList(SearchedUser.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(total_count)
        writeInt((if (incompleteResults) 1 else 0))
        writeTypedList(userList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchResultResponse> = object : Parcelable.Creator<SearchResultResponse> {
            override fun createFromParcel(source: Parcel): SearchResultResponse = SearchResultResponse(source)
            override fun newArray(size: Int): Array<SearchResultResponse?> = arrayOfNulls(size)
        }
    }
}