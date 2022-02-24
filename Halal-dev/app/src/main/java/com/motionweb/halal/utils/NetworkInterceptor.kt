package com.motionweb.halal.utils

import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import okhttp3.Interceptor
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(private val appPref: AppPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${appPref.accessToken}")
                .build()
        )
    }
}