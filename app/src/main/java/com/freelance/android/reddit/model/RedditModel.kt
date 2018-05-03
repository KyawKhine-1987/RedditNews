package com.freelance.android.reddit.model

import com.google.gson.annotations.SerializedName

data class RedditModel(
        @SerializedName("data") val data: ParentData) {

    data class ParentData(
            @SerializedName("children") val children: List<Children> = listOf(),
            @SerializedName("after") val after: String,
            @SerializedName("before") val before: Any) {

        data class Children(
                @SerializedName("data") val data: Data) {

            data class Data(
                    @SerializedName("author") val author: String,
                    @SerializedName("thumbnail") val thumbnail: String,
                    @SerializedName("created") val created: Long,
                    @SerializedName("url") val url: String,
                    @SerializedName("num_comments") val num_comments: Int,
                    @SerializedName("title") val title: String)
        }
    }
}
