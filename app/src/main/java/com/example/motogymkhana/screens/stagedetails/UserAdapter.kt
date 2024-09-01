package com.example.motogymkhana.screens.stagedetails

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.ItemUserBinding
import com.example.motogymkhana.model.Attempt
import com.example.motogymkhana.model.IsActive
import com.example.motogymkhana.model.UserResultState
import com.example.motogymkhana.model.UserStatus

interface UserListener {
    fun showMenu(user: UserResultState, attempt: Attempt)

    fun setActive(isActive: IsActive, participantID: Long)

    fun closeMenu()
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
    private val userListener: UserListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : ViewHolder(binding.root) {

        fun onBind(item: UserResultState, listener: UserListener) = with(binding) {

            when (item.userStatus) {
                UserStatus.FINISHED -> {
                    usersNumberTextView.setTextColor(Color.WHITE)
                    usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
                }

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

            when (item.isActive) {
                IsActive.FIRST -> {
                    firstAttemptTimeTextView.setBackgroundResource(R.drawable.text_bg)
                    secondAttemptTimeTextView.setBackgroundResource(R.color.primary)
                }

                IsActive.SECOND -> {
                    firstAttemptTimeTextView.setBackgroundResource(R.color.primary)
                    secondAttemptTimeTextView.setBackgroundResource(R.drawable.text_bg)
                }

                IsActive.INACTIVE -> {
                    firstAttemptTimeTextView.setBackgroundResource(R.color.primary)
                    secondAttemptTimeTextView.setBackgroundResource(R.color.primary)
                }
            }

            userNameTextView.text = item.userFullName
            userDetailsTextView.text = "${item.athleteClass} ${item.userCity}"
            groupTextView.text = item.champClass
            usersNumberTextView.text = item.number

            val firstAttempt = item.attempts.getOrNull(0)
            if (firstAttempt != null) {
                if (firstAttempt.fine != null && firstAttempt.fine > 0) {
                    firstAttemptFineTextView.setTextColor(
                        when (firstAttempt.fine) {
                            in 1..3 -> Color.parseColor("#dde80e")
                            in 4..5 -> Color.parseColor("#eb910c")
                            else -> Color.parseColor("#e81c0e")
                        }
                    )
                    firstAttemptFineTextView.text = "+${firstAttempt.fine}"
                    firstAttemptFineTextView.isVisible = true
                } else {
                    firstAttemptFineTextView.isVisible = false
                }
                firstAttemptTimeTextView.setText(
                    if (!firstAttempt.isFail) {
                        firstAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                        firstAttempt.time
                    } else {
                        firstAttemptTimeTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        firstAttempt.time
                    }
                )
            } else {
                firstAttemptTimeTextView.setText("00:00.00")
                firstAttemptFineTextView.isVisible = false
                firstAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
            firstAttemptTimeTextView.imeOptions = EditorInfo.IME_ACTION_DONE

            val secondAttempt = item.attempts.getOrNull(1)
            if (secondAttempt != null) {
                if (secondAttempt.fine != null && secondAttempt.fine > 0) {
                    secondAttemptFineTextView.setTextColor(
                        when (secondAttempt.fine) {
                            in 1..3 -> Color.parseColor("#dde80e")
                            in 4..5 -> Color.parseColor("#eb910c")
                            else -> Color.parseColor("#e81c0e")
                        }
                    )
                    secondAttemptFineTextView.text = "+${secondAttempt.fine}"
                    secondAttemptFineTextView.isVisible = true
                } else {
                    secondAttemptFineTextView.isVisible = false
                }
                secondAttemptTimeTextView.setText(
                    if (!secondAttempt.isFail) {
                        secondAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                        secondAttempt.time
                    } else {
                        secondAttemptTimeTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        secondAttempt.time
                    }
                )
            } else {
                secondAttemptTimeTextView.setText("00:00.00")
                secondAttemptFineTextView.isVisible = false
                secondAttemptTimeTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
            secondAttemptTimeTextView.imeOptions = EditorInfo.IME_ACTION_DONE

            resultTimeTextView.text = item.bestTime

            itemView.setOnClickListener {
                listener.closeMenu()
            }

            firstAttemptTimeTextView.setOnClickListener {
                listener.setActive(IsActive.FIRST, item.participantID)
                listener.showMenu(user = item, attempt = Attempt.First)
            }

            secondAttemptTimeTextView.setOnClickListener {
                listener.setActive(IsActive.SECOND, item.participantID)
                listener.showMenu(user = item, attempt = Attempt.Second)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        UserViewHolder(binding)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.onBind(items[position], userListener)

    }

    override fun getItemCount(): Int = items.size
}