package org.isen.papernews.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SourceInformation(val status: String, val sources: List<Source>) {
    class Deserializer : ResponseDeserializable<SourceInformation> {
        override fun deserialize(content: String): SourceInformation? = Gson().fromJson(content, SourceInformation::class.java)
    }
}

data class Source(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)