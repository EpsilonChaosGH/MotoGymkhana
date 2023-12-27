package com.example.motogymkhana.screens.stages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.motogymkhana.databinding.ItemStageBinding
import com.example.motogymkhana.model.StageState

interface StageListener {
}

class StageDiffCallback(
    private val oldList: List<StageState>,
    private val newList: List<StageState>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList.stageID == newList.stageID
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
        private val binding: ItemStageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: StageState, listener: StageListener) = with(binding) {

            dataTextView.text = item.dateOfThe
            stageTitleTextView.text = item.title
        }
    }

    var items = listOf<StageState>()
        set(newValue) {
            val diffCallback = StageDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], stageListener)
    }

    override fun getItemCount(): Int = items.size
}