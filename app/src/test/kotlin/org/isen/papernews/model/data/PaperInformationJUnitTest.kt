package org.isen.papernews.model.data

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.papernews.data.PaperInformation
import kotlin.test.*

class PaperInformationJUnitTest {
    @Test
    fun GetNumberOfArticlesFromJson() {
        val (request, response, result)  = "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com,engadget.com&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
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
        val (request, response, result)  = "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com,engadget.com&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(
            PaperInformation.Deserializer()
        )

        assertTrue(response.isSuccessful)
        val(si,error) = result
        if (si!=null) {
            assertEquals("techcrunch", si.articles[0].source.id)
            assertEquals("TechCrunch", si.articles[0].source.name)
            assertEquals("Manish Singh", si.articles[0].author)
            assertEquals("Is India done with crypto?", si.articles[0].title)
            assertEquals("It wasn’t long ago when Indian venture capitalists were scrambling to establish their crypto credentials. Ethereum wallet addresses adorned Twitter profiles. Over a dozen VC firms scrambled to publish their own web3 investment theses, some even lowering their…", si.articles[0].description)
            assertEquals("https://techcrunch.com/2024/01/10/is-india-done-with-crypto/", si.articles[0].url.toString())
            assertEquals("https://techcrunch.com/wp-content/uploads/2022/08/GettyImages-1042683346.jpg?resize=1200,899", si.articles[0].urlToImage.toString())
            assertEquals("Thu Jan 11 08:31:59 CET 2024", si.articles[0].publishedAt.toString())
            assertEquals("It wasn’t long ago when Indian venture capitalists were scrambling to establish their crypto credentials. Ethereum wallet addresses adorned Twitter profiles. Over a dozen VC firms scrambled to publis… [+3723 chars]", si.articles[0].content)

        } else {
            fail("error")
        }
    }

}