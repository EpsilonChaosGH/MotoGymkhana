package com.example.motogymkhana.screens.championships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.ItemChampionshipBinding
import com.example.motogymkhana.databinding.ItemStageBinding
import com.example.motogymkhana.model.ChampionshipState
import com.example.motogymkhana.model.StageState
import kotlinx.coroutines.launch

interface ChampionshipsListener {

    fun showStagesList(championship: ChampionshipState)
}

class StageDiffCallback(
    private val oldList: List<ChampionshipState>,
    private val newList: List<ChampionshipState>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList.id == newList.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList == newList
    }
}

class ChampionshipAdapter(
    private val listener: ChampionshipsListener
) : RecyclerView.Adapter<ChampionshipAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemChampionshipBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ChampionshipState, listener: ChampionshipsListener) = with(binding) {

            itemView.setOnClickListener { listener.showStagesList(item) }

            dataTextView.text = item.year.toString()
            titleTextView.text = item.title
        }
    }

    var items = listOf<ChampionshipState>()
        set(newValue) {
            val diffCallback = StageDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChampionshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}