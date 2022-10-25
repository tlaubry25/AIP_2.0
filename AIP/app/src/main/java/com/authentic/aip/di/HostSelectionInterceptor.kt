package com.authentic.aip.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HostSelectionInterceptor : Interceptor {
    @Volatile
    private var host: String? = null
    private var port :Int? = 0
    fun setHost(host: String?) {
        if(host!=null){
            this.host = host
        }
    }
    fun setPort(port: Int?) {
        if(port!=null){
            this.port = port
        }
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val reqUrl: String = request.url.host

        if (host != null && port!=null) {
            val newUrl: HttpUrl = request.url.newBuilder()
                .scheme("https")
                .host(host!!)
                .port(port!!)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}