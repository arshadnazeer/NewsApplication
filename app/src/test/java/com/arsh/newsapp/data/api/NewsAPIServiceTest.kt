package com.arsh.newsprojectpract.data.api

import com.arsh.newsapp.data.api.NewsAPIService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadLines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=a9ea651e5a1e4af6a1da4da5d61995a5")
        }
    }

    @Test
    fun getTopHeadLines_sentRequest_recievedNumberOfArticles() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articlList = responseBody!!.articles
            assertThat(articlList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_sendRequest_correctContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            val article = articleList[0]
            assertThat(article.author).isEqualTo("Brynn Gingras, Shimon Prokupecz, Pervaiz Shallwani, Artemis Moshtaghian, Laura Ly, Kristina Sgueglia and Eric Levenson, CNN")
            assertThat(article.content).isEqualTo(
                "" +
                        "New York (CNN)The man suspected of shooting 10 people on a subway train in Brooklyn on Tuesday was arrested by patrol officers in Manhattan's East Village neighborhood on Wednesday afternoon, officia??? [+9504 chars]"
            )
            assertThat(article.publishedAt).isEqualTo("2022-04-13T20:29:00Z")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}