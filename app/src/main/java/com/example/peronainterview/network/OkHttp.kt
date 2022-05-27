package com.example.peronainterview.network

import com.example.peronainterview.database.WeatherForecast
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resumeWithException

class OkHttp {

    private val url = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-D083C911-955C-4EE3-9DBB-36AA3A03F48A&format=JSON&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=MinT"
    private val client = OkHttpClient()
    private val parseJson = ParseJson()

    suspend fun getWeatherData(): List<WeatherForecast> {
        val request = Request.Builder().url(url).get().build()
        val call = client.newCall(request)
        return suspendCancellableCoroutine {
            call.enqueue(object : Callback {
                //若失敗則印出警告訊息
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed to execute Request")
                    it.resumeWithException(e)
                }
                //若成功則印出內容
                override fun onResponse(call: Call, response: Response) {
                    println("RequestUrl: $url")
                    if (response.isSuccessful) {
                        response.body?.string()?.let { body ->
                            val result = parseJson.parseWeather(body)
                            println("Response: $body")
                            it.resumeWith(Result.success(result))
                        }
                    }else{
                        it.resumeWithException(Exception("Get fail. response ${response.code}"))
                    }
                }
            })
            it.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}