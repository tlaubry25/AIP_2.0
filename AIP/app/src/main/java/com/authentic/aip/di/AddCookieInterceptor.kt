package com.authentic.aip.di

import com.authentic.aip.framework.App.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookieInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder:Request.Builder = chain.request().newBuilder()


        val prefCookie:HashSet<String> = prefs?.preferences?.getStringSet("PREF_COOKIES", HashSet()) as HashSet<String>
        for(cookie in prefCookie){
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}