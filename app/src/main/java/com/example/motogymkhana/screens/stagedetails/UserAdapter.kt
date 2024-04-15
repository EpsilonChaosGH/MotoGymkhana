package com.example.motogymkhana.screens.stagedetails

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.text.set
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.ItemUserBinding
import com.example.motogymkhana.databinding.ItemUserMenuBinding
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.model.UserResultState
import com.example.motogymkhana.model.UserStatus
import java.lang.IllegalStateException

interface UserListener {
    fun openTimeMenu(participantID: Long)

    fun saveTime(requestBody: PostTimeRequestBody)

    fun getCurrentTime(): String
}

class UserDiffCallback(
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

class UserAdapter(
    private val stageListener: UserListener
) : RecyclerView.Adapter<ViewHolder>() {
    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : ViewHolder(binding.root) {

        fun onBind(item: UserResultState, listener: UserListener) = with(binding) {

            when (item.userStatus) {
                UserStatus.RIDES -> {
                    usersNumberTextView.setTextColor(Color.WHITE)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.NEXT -> {
                    usersNumberTextView.setTextColor(Color.WHITE)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.HEATING -> {
                    usersNumberTextView.setTextColor(Color.BLACK)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.WAITING -> {
                    usersNumberTextView.setTextColor(Color.BLACK)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }
            }

            userNameTextView.text = item.userFullName
            userDetailsTextView.text = "${item.champClass} ${item.userCity}"
            groupTextView.text = item.champClass
            usersNumberTextView.text = "48"

            val firstAttempt = item.attempts.getOrNull(0)
            if (firstAttempt != null) {
                firstAttemptTimeTextView.setText(
                    if (!firstAttempt.isFail) {
                        firstAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                        firstAttempt.resultTime
                    } else {
                        firstAttemptTimeTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        firstAttempt.resultTime
                    }
                )
            } else {
                firstAttemptTimeTextView.setText("00:00.00")
            }
            firstAttemptTimeTextView.imeOptions = EditorInfo.IME_ACTION_DONE

            val secondAttempt = item.attempts.getOrNull(1)
            if (secondAttempt != null) {
                secondAttemptTimeTextView.setText(
                    if (!secondAttempt.isFail) {
                        secondAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                        secondAttempt.resultTime
                    } else {
                        secondAttemptTimeTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        secondAttempt.resultTime
                    }
                )
            } else {
                secondAttemptTimeTextView.setText("00:00.00")
            }
            secondAttemptTimeTextView.imeOptions = EditorInfo.IME_ACTION_DONE

            resultTimeTextView.text = item.bestTime

            itemView.setOnClickListener {
                listener.openTimeMenu(item.participantID)
            }
        }
    }

    class UserMenuViewHolder(
        private val binding: ItemUserMenuBinding
    ) : ViewHolder(binding.root) {

        fun onBind(item: UserResultState, listener: UserListener) = with(binding) {

            when (item.userStatus) {
                UserStatus.RIDES -> {
                    usersNumberTextView.setTextColor(Color.WHITE)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.NEXT -> {
                    usersNumberTextView.setTextColor(Color.WHITE)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.HEATING -> {
                    usersNumberTextView.setTextColor(Color.BLACK)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

                UserStatus.WAITING -> {
                    usersNumberTextView.setTextColor(Color.BLACK)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }
            }

            userNameTextView.text = item.userFullName
            userDetailsTextView.text = "${item.champClass} ${item.userCity}"
            groupTextView.text = item.champClass
            usersNumberTextView.text = "48"

            val firstAttempt = item.attempts.getOrNull(0)
            if (firstAttempt != null) {
                firstAttemptEditTextTime.setText(
                    if (!firstAttempt.isFail) {
                        firstAttemptEditTextTime.paintFlags = Paint.ANTI_ALIAS_FLAG
                        firstAttempt.resultTime
                    } else {
                        firstAttemptEditTextTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        firstAttempt.resultTime
                    }
                )
            } else {
                firstAttemptEditTextTime.setText("00:00.00")
            }
            firstAttemptEditTextTime.imeOptions = EditorInfo.IME_ACTION_DONE

            val secondAttempt = item.attempts.getOrNull(1)
            if (secondAttempt != null) {
                secondAttemptEditTextTime.setText(
                    if (!secondAttempt.isFail) {
                        secondAttemptEditTextTime.paintFlags = Paint.ANTI_ALIAS_FLAG
                        secondAttempt.resultTime
                    } else {
                        secondAttemptEditTextTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        secondAttempt.resultTime
                    }
                )
            } else {
                secondAttemptEditTextTime.setText("00:00.00")
            }
            secondAttemptEditTextTime.imeOptions = EditorInfo.IME_ACTION_DONE

            resultTimeTextView.text = item.bestTime

            itemView.setOnClickListener {
                listener.openTimeMenu(item.participantID)
            }

            firstAttemptGetCurrentTimeButton.setOnClickListener {
                firstAttemptEditTextTime.setText(listener.getCurrentTime())
            }

            secondAttemptGetCurrentTimeButton.setOnClickListener {
                secondAttemptEditTextTime.setText(listener.getCurrentTime())
            }

            firstAttemptSaveButton.setOnClickListener {
                listener.saveTime(
                    PostTimeRequestBody(
                        stageId = "66",
                        participantID = item.participantID.toString(),
                        attempt = "1",
                        time = firstAttemptEditTextTime.text.toString(),
                        fine = firstAttemptPicker.getValue().toString(),
                        isFail = if (firstAttemptSwitch.isChecked) {
                            "1"
                        } else {
                            "0"
                        }
                    )
                )
            }

            secondAttemptSaveButton.setOnClickListener {
                listener.saveTime(
                    PostTimeRequestBody(
                        stageId = "66",
                        participantID = item.participantID.toString(),
                        attempt = "2",
                        time = secondAttemptEditTextTime.text.toString(),
                        fine = secondAttemptPicker.getValue().toString(),
                        isFail = if (secondAttemptSwitch.isChecked) {
                            "1"
                        } else {
                            "0"
                        }
                    )
                )
            }
        }
    }

    var items = listOf<UserResultState>()
        set(newValue) {
            val diffCallback = UserDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_user -> {
                val binding =
                    ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserViewHolder(binding)
            }

            R.layout.item_user_menu -> {
                val binding =
                    ItemUserMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserMenuViewHolder(binding)
            }

            else -> {
                throw IllegalStateException("Illegal viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!items[position].openTimeMenu) {
            (holder as UserViewHolder).onBind(items[position], stageListener)
        } else {
            (holder as UserMenuViewHolder).onBind(items[position], stageListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!items[position].openTimeMenu) {
            R.layout.item_user
        } else {
            R.layout.item_user_menu
        }
    }

    override fun getItemCount(): Int = items.size
}