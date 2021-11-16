package dev.antoineadam.debugview.ui

import androidx.lifecycle.ViewModel
import dev.antoineadam.debugview.Mock

class DebugViewSharedViewModel : ViewModel() {
    var mockList = listOf<Mock>()
}