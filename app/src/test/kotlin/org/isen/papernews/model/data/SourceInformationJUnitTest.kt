package org.isen.papernews.model.data

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.papernews.data.SourceInformation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class SourceInformationJUnitTest {
    @Test
    fun GetNumberOfSourceFromJson(){
        val (request,response,result) = "https://newsapi.org/v2/top-headlines/sources?apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
            SourceInformation.Deserializer())
        assertTrue(response.isSuccessful)
        val(si,error) = result
        if(si!=null){
            assertTrue(si.sources.size>0)
            assertEquals("ok", si.status)

        } else {
            fail("error")
        }
    }

    @Test
    fun GetFirstSourceInformationFromJson(){
        val (request,response,result) = "https://newsapi.org/v2/top-headlines/sources?apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
            SourceInformation.Deserializer())
        assertTrue(response.isSuccessful)
        val(si,error) = result
        if(si!=null){
            assertEquals("abc-news", si.sources[0].id)
            assertEquals("ABC News", si.sources[0].name)
            assertEquals("Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.", si.sources[0].description)
            assertEquals("https://abcnews.go.com", si.sources[0].url)
            assertEquals("general", si.sources[0].category)
            assertEquals("en", si.sources[0].language)
            assertEquals("us", si.sources[0].country)
        } else {
            fail("error")
        }
    }

}