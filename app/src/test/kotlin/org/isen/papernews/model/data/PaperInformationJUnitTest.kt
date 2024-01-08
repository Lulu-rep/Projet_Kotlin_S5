package org.isen.papernews.model.data

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.papernews.data.PaperInformation
import kotlin.test.*

class PaperInformationJUnitTest {
    @Test
    fun GetNumberOfArticlesFromJson() {
        val (request, response, result)  = "https://newsapi.org/v2/everything?q=tesla&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
            PaperInformation.Deserializer()
        )

        assertTrue(response.isSuccessful)
        val(si,error) = result
        if (si!=null){
            assertEquals("ok", si.status)
            assertEquals(100, si.articles.size)
            assertNotEquals(0, si.totalResults)
        } else {
            fail("error")
        }
    }

    @Test
    fun getFirstArticleInformationFromJson(){
        val (request, response, result)  = "https://newsapi.org/v2/everything?q=tesla&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
            PaperInformation.Deserializer()
        )

        assertTrue(response.isSuccessful)
        val(si,error) = result
        if (si!=null) {
            assertEquals("wired", si.articles[0].source.id)
            assertEquals("Wired", si.articles[0].source.name)
            assertEquals("Morgan Meaker", si.articles[0].author)
            assertEquals("Tesla Is Suing Sweden", si.articles[0].title)
            assertEquals("Unions are boycotting Tesla’s business operations in Sweden to pressure it to sign an agreement with its workers. Now Tesla has decided to sue.", si.articles[0].description)
            assertEquals("https://www.wired.com/story/tesla-sues-sweden-union-action/", si.articles[0].url.toString())
            assertEquals("https://media.wired.com/photos/6564db3faaee1faccb9e326a/191:100/w_1280,c_limit/tesla-labor-biz-GettyImages-1747826024.jpg", si.articles[0].urlToImage.toString())
            assertEquals("Mon Nov 27 19:11:03 CET 2023", si.articles[0].publishedAt.toString())
        } else {
            fail("error")
        }
    }

}