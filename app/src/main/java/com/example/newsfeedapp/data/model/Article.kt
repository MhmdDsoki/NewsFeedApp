package  com.example.newsfeedapp.data.model



import com.google.gson.annotations.SerializedName



data class Article(


    @SerializedName("author")
    val author: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?


)