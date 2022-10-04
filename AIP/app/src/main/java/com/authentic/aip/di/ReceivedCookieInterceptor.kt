package com.authentic.aip.di

import com.authentic.aip.framework.App.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse : Response = chain.proceed(chain.request())
        if(originalResponse.headers("Set-Cookie").isNotEmpty()){
            val cookies : HashSet<String> = HashSet()
            for(header in originalResponse.headers("Set-Cookie")){
                cookies.add(header)
            }
            prefs?.preferences?.edit()?.putStringSet("PREF_COOKIES", cookies)?.commit()
        }
        return originalResponse
    }
}