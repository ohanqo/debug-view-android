package dev.antoineadam.debugview.interceptor

import android.content.Context
import okhttp3.OkHttpClient.Builder

fun Builder.addDebugViewInterceptors(context: Context): Builder {
    addInterceptor(ChuckerInterceptor(context))
    addInterceptor(MockInterceptor(context.assets))
    return this
}