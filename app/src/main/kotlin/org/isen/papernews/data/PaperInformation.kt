package org.isen.papernews.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.net.URL
import java.util.Date

data class PaperInformation(
    val status: String,
    val totalResults: Int,
    val articles: List<InfoArticle>
){
    class Deserializer : ResponseDeserializable<PaperInformation>{
        override fun deserialize(content: String): PaperInformation {
            return Gson().fromJson(content, PaperInformation::class.java)
        }
    }
}

data class InfoArticle(
    val source: SourceArt,
    val author: String,
    val title: String,
    val description: String,
    val url: URL,
    val urlToImage: URL,
    val publishedAt: Date,
    val content: String
)

data class SourceArt(
    val id: String,
    val name: String
)