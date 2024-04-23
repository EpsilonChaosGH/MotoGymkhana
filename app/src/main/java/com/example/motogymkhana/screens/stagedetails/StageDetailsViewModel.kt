package com.example.motogymkhana.screens.stagedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.mappers.toStageState
import com.example.motogymkhana.model.FirebaseStatus
import com.example.motogymkhana.model.IsActive
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.model.UserStatus
import com.example.motogymkhana.screens.settings.SettingsSharedPref
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.utils.WhileUiSubscribed
import com.example.motogymkhana.utils.millisecondToMinutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StageDetailsViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository,
    private val client: OkHttpClient,
    private val settings: SettingsSharedPref
) : ViewModel() {

//    private val myRef = Firebase.database.getReference(Const.TEST_DB_KEY)

    private val _stage = MutableStateFlow<StageState?>(null)
    private val _currentTime = MutableStateFlow("00:00.00")
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val list = listOf(
        FirebaseStatus(participantID = 792, UserStatus.RIDES),
        FirebaseStatus(participantID = 793, UserStatus.WAITING),
        FirebaseStatus(participantID = 794, UserStatus.HEATING),
        FirebaseStatus(participantID = 795, UserStatus.NEXT),
    )

    val uiState: StateFlow<StageDetailsScreenState?> = combine(
        _stage,
        _currentTime,
        _userMessage,
        _isLoading,
    ) { stageInfo, currentTime, userMessage, isLoading ->
        Log.e("aaaerror", "exception.message.toString()")
        StageDetailsScreenState(
            stageState = stageInfo,
            currentTime = currentTime,
            userMessage = userMessage,
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.stackTrace
        Log.e("aaaerror", exception.message.toString())
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    fun setActive(isActive: IsActive, participantID: Long){
            _stage.value?.let {
                _stage.value = _stage.value?.copy(results = it.results.map { user ->
                    if (user.participantID == participantID) {
                        user.copy(isActive = isActive)
                    } else {
                        user.copy(isActive = IsActive.INACTIVE)
                    }
                })
            }
    }

    fun postTime(requestBody: PostTimeRequestBody) {
        viewModelScope.launch(exceptionHandler) {
            if (gymkhanaCupRepository.postTime(requestBody).isSuccessful) {
                _stage.value?.stageID?.let {
                    getStageInfo(id = it.toString(), type = Type.Offline.value)
                }
            }
        }
    }

    init {
        getCurrentTimeMok()
//        getCurrentTime()
    }

    private fun getCurrentTime() {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://${settings.getControllerIp()}/${settings.getRequest()}").build()
            val response = client.newCall(request).execute()
            val time = response.getResult().toDouble().millisecondToMinutes()
            _currentTime.value = time
        }
    }

    private fun getCurrentTimeMok() {
        viewModelScope.launch {
            repeat(1000) {
                delay(1000)
                Log.e("aaa","asdasd")
//                getCurrentTime()
                _currentTime.value = "00:${Random.nextInt(10, 60)}.${Random.nextInt(10, 60)}"
            }
        }
    }

    fun getStageInfo(id: String, type: String) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stage.value = gymkhanaCupRepository.getStage(id, type).toStageState()
            setLoading(false)
        }
    }

    private fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}

//class UserMenuViewHolder(
//    private val binding: ItemUserMenuBinding
//) : RecyclerView.ViewHolder(binding.root) {
//
//    fun onBind(item: UserResultState, listener: UserListener) = with(binding) {
//
//        when (item.userStatus) {
//            UserStatus.RIDES -> {
//                usersNumberTextView.setTextColor(Color.WHITE)
//                usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
//            }
//
//            UserStatus.NEXT -> {
//                usersNumberTextView.setTextColor(Color.WHITE)
//                usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
//            }
//
//            UserStatus.HEATING -> {
//                usersNumberTextView.setTextColor(Color.BLACK)
//                usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
//            }
//
//            UserStatus.WAITING -> {
//                usersNumberTextView.setTextColor(Color.BLACK)
//                usersNumberTextView.setBackgroundResource(item.userStatus.colorResId)
//            }
//        }
//
//        userNameTextView.text = item.userFullName
//        userDetailsTextView.text = "${item.champClass} ${item.userCity}"
//        groupTextView.text = item.champClass
//        usersNumberTextView.text = item.number
//
//        val firstAttempt = item.attempts.getOrNull(0)
//        if (firstAttempt != null) {
//            firstAttemptEditTextTime.setText(
//                if (!firstAttempt.isFail) {
//                    firstAttemptEditTextTime.paintFlags = Paint.ANTI_ALIAS_FLAG
//                    firstAttempt.resultTime
//                } else {
//                    firstAttemptEditTextTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                    firstAttempt.resultTime
//                }
//            )
//        } else {
//            firstAttemptEditTextTime.setText("00:00.00")
//        }
//        firstAttemptEditTextTime.imeOptions = EditorInfo.IME_ACTION_DONE
//
//        val secondAttempt = item.attempts.getOrNull(1)
//        if (secondAttempt != null) {
//            secondAttemptEditTextTime.setText(
//                if (!secondAttempt.isFail) {
//                    secondAttemptEditTextTime.paintFlags = Paint.ANTI_ALIAS_FLAG
//                    secondAttempt.resultTime
//                } else {
//                    secondAttemptEditTextTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                    secondAttempt.resultTime
//                }
//            )
//        } else {
//            secondAttemptEditTextTime.setText("00:00.00")
//        }
//        secondAttemptEditTextTime.imeOptions = EditorInfo.IME_ACTION_DONE
//
//        resultTimeTextView.text = item.bestTime
//
//        itemView.setOnClickListener {
//            listener.openTimeMenu(item.participantID)
//        }
//
//        firstAttemptGetCurrentTimeButton.setOnClickListener {
//            firstAttemptEditTextTime.setText(listener.getCurrentTime())
//        }
//
//        secondAttemptGetCurrentTimeButton.setOnClickListener {
//            secondAttemptEditTextTime.setText(listener.getCurrentTime())
//        }
//
//        firstAttemptSaveButton.setOnClickListener {
//            listener.saveTime(
//                PostTimeRequestBody(
//                    stageId = "66",
//                    participantID = item.participantID.toString(),
//                    attempt = "1",
//                    time = firstAttemptEditTextTime.text.toString(),
//                    fine = firstAttemptPicker.getValue().toString(),
//                    isFail = if (firstAttemptSwitch.isChecked) {
//                        "1"
//                    } else {
//                        "0"
//                    }
//                )
//            )
//        }
//
//        secondAttemptSaveButton.setOnClickListener {
//            listener.saveTime(
//                PostTimeRequestBody(
//                    stageId = "66",
//                    participantID = item.participantID.toString(),
//                    attempt = "2",
//                    time = secondAttemptEditTextTime.text.toString(),
//                    fine = secondAttemptPicker.getValue().toString(),
//                    isFail = if (secondAttemptSwitch.isChecked) {
//                        "1"
//                    } else {
//                        "0"
//                    }
//                )
//            )
//        }
//    }
//}


//init {
//    val listener = object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            var str = ""
//            val size = snapshot.children.toList().size
//            snapshot.children.forEach {
//                Log.e("aaa", " snapshot.children.forEach")
//
//                val firebaseStatus = it.getValue(FirebaseStatus::class.java)
//
//                firebaseStatus?.let {
//                    Log.e("aaa", it.userId.toString())
//                    Log.e("aaa", it.userStatus.toString())
//                }
//
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            showMessage(R.string.error)
//        }
//    }
//
//    //getCurrentTime()
//    myRef.addValueEventListener(listener)
//}

//    private fun saveData() {
//        myRef.push().updateChildren(mapOf("111" to FirebaseStatus(userId = 1585, UserStatus.RIDES)))
////        myRef.push().setValue(Test(testString = binding.textView.text.toString()))
////        myRef.push().setValue(Test(testString = "TEXT"))
//    }

//        private fun saveData(){
//        myRef.push().setValue(Test(testString = binding.textView.text.toString()))
//            myRef.push().updateChildren(mapOf("111" to Test(Random.nextInt(1000), "1-1")))
//        myRef.push().setValue(Test(testString = "TEXT"))
//        }

//data class Test(
//    val testInt: Int = 12,
//    val testString: String = "str"
//)