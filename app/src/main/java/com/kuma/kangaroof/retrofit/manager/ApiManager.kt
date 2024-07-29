package com.kuma.kangaroof.retrofit.manager

import android.util.Log
import com.kuma.kangaroof.retrofit.BASE_URL
import com.kuma.kangaroof.retrofit.api.ApiInterface
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset


object ApiManager {

    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .client(initOkhttpClient())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api by lazy { mRetrofit.create(ApiInterface::class.java) }

    private fun initOkhttpClient(): OkHttpClient {

        val build = OkHttpClient.Builder()
        build.addInterceptor(CustomLogInterceptor())

        return build.build()

    }


}

class CustomLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        printInfo(request, response)
        return response
    }

    private fun printInfo(request: Request, response: Response) {
        val builder = StringBuilder()
        val logInfo = """START HTTP->请求方法-->：${request.method} ${request.url} 
 请求头-->：${getRequestHeaders(request)} 
 请求参数-->：${getRequestParams(request)} 
 返回结果-->：${getResponseText(response)}
 
 END HTTP->"""

        Log.i("network", logInfo)
    }

    private fun getResponseText(response: Response): String {
        var str = "Empty!"
        try {
            val body = response.body
            if (body != null && body.contentLength() != 0L) {
                val source = body.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer()
                val mediaType = body.contentType()
                if (mediaType != null) {
                    val charset = mediaType.charset(
                        Charset.forName("UTF-8")
                    )
                    if (charset != null) {
                        str = buffer.clone().readString(charset)
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return str
    }


    private fun getRequestParams(request: Request): String {
        var str = "Empty!"
        try {
            val body = request.body
            if (body != null) {
                val buffer: Buffer = Buffer()
                body.writeTo(buffer)
                val mediaType: MediaType? = body.contentType()
                if (mediaType != null) {
                    val charset: Charset? = mediaType.charset(
                        Charset.forName("UTF-8")
                    )
                    str = if (charset != null) {
                        buffer.readString(charset)
                    } else {
                        buffer.readUtf8()
                    }
                } else {
                    str = buffer.readUtf8()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    private fun getRequestHeaders(request: Request): String {
        val headers: Headers = request.headers
        val str = "Empty!"
        val builder = java.lang.StringBuilder()
        if (headers.size > 0) {
            var i = 0
            val count: Int = headers.size
            while (i < count) {
                builder.append(headers.name(i) + ": " + headers.value(i)).append(" \r\n ")
                i++
            }
            return builder.toString()
        } else {
            return "Empty!"
        }
    }
}
