package dev.antoineadam.debugview.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.chuckerteam.chucker.api.Chucker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.antoineadam.debugview.databinding.FragmentDebugViewBinding

class DebugViewFragment : BottomSheetDialogFragment() {
    private val adapter = MockAdapter()
    private val viewModel by activityViewModels<DebugViewSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDebugViewBinding.inflate(inflater, container, false).apply {
        debugViewMockRecycler.adapter = adapter
        debugViewChuckerButton.setOnClickListener { openChuckerActivity() }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(viewModel.mockList)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = dialog?.findViewById<FrameLayout>(
                    com.google.android.material.R.id.design_bottom_sheet
                ) ?: return@setOnShowListener

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun openChuckerActivity() = context?.let {
        val intent = Chucker.getLaunchIntent(it)
        startActivity(intent)
    }

    companion object {
        const val TAG = "DEBUG_VIEW"
    }
}
