package  com.example.newsfeedapp.data.model


import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("sortBy")
    val sortBy: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("status")
    val status: String?
)