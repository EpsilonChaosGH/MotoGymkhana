package com.example.motogymkhana.screens.stage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.motogymkhana.databinding.ItemUserBinding
import com.example.motogymkhana.model.UserResultState

interface StageListener {
}

class StageDiffCallback(
    private val oldList: List<UserResultState>,
    private val newList: List<UserResultState>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList.userFullName == newList.userFullName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList == newList
    }
}

class StageAdapter(
    private val stageListener: StageListener
) : RecyclerView.Adapter<StageAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: UserResultState, listener: StageListener) = with(binding) {

            userTextView.text = item.userFullName
            timeTextView.text = item.bestTime
        }
    }

    var items = listOf<UserResultState>()
        set(newValue) {
            val diffCallback = StageDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], stageListener)
    }

    override fun getItemCount(): Int = items.size
}