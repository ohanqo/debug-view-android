package dev.antoineadam.debugview.ui

import android.hardware.SensorManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.seismic.ShakeDetector
import dev.antoineadam.debugview.BuildConfig
import dev.antoineadam.debugview.Mock
import kotlin.reflect.KClass

abstract class AbstractDebugViewActivity : AppCompatActivity(), ShakeDetector.Listener {
    private val viewModel by viewModels<DebugViewSharedViewModel>()

    fun initDebugView(vararg classList: KClass<*>) {
        if (BuildConfig.DEBUG) {
            registerMockList(classList)
            listenToShake()
        }
    }

    private fun registerMockList(classList: Array<out KClass<*>>) {
        val mockList = classList.flatMap {
            it.sealedSubclasses.mapNotNull { sub ->
                sub.objectInstance as? Mock?
            }
        }
        viewModel.mockList = mockList
    }

    private fun listenToShake() {
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector(this)
        shakeDetector.start(sensorManager)
    }

    override fun hearShake() {
        if (supportFragmentManager.findFragmentByTag(DebugViewFragment.TAG) == null && !supportFragmentManager.isDestroyed) {
            DebugViewFragment().show(supportFragmentManager, DebugViewFragment.TAG)
        }
    }
}