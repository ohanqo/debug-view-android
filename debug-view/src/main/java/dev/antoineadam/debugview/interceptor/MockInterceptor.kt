package dev.antoineadam.debugview.interceptor

import android.content.res.AssetManager
import dev.antoineadam.debugview.BuildConfig
import dev.antoineadam.debugview.Mockable
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation

class MockInterceptor(private val assets: AssetManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.tag(Invocation::class.java)?.method()
        val annotation = method?.getAnnotation(Mockable::class.java)
        val mock = annotation?.mock?.first()?.objectInstance

        // Check if in debug mode
        return if (BuildConfig.DEBUG && mock?.isActive == true) {
            val dataStream = assets.open(mock.assetPath)
            val json = dataStream.bufferedReader().use { it.readText() }
            val body = json.toResponseBody("application/json".toMediaTypeOrNull())

            Response.Builder()
                .code(200)
                .body(body)
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .message(mock.title)
                .build()
        } else {
            chain.proceed(request)
        }
    }
}
