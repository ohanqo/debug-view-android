package dev.antoineadam.debugview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.antoineadam.debugview.Mock
import dev.antoineadam.debugview.databinding.ItemMockCheckboxViewBinding

class MockAdapter : ListAdapter<Mock, MockAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMockCheckboxViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemMockCheckboxViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Mock) {
            binding.mock = item
            binding.mockCheckbox.isChecked = item.isActive
            binding.mockCheckbox.setOnClickListener { item.isActive = !item.isActive }
            binding.executePendingBindings()
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Mock>() {
        override fun areItemsTheSame(oldItem: Mock, newItem: Mock): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Mock, newItem: Mock): Boolean {
            return oldItem.isActive == newItem.isActive
        }
    }
}
