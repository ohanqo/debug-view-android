package dev.antoineadam.debugview.interceptor

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.Interceptor

object ChuckerInterceptor {
    operator fun invoke(context: Context): Interceptor {
        val collector = ChuckerCollector(
            context = context,
            showNotification = false,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor
            .Builder(context)
            .collector(collector)
            .build()
    }
}