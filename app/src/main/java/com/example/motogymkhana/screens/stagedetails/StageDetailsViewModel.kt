package com.example.motogymkhana.screens.stagedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.mappers.toStageInfoState
import com.example.motogymkhana.model.FirebaseStatus
import com.example.motogymkhana.model.StageInfoState
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.model.UserStatus
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.utils.WhileUiSubscribed
import com.example.motogymkhana.utils.millisecondToMinutes
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StageDetailsViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository,
    private val client: OkHttpClient,
) : ViewModel() {

//    private val myRef = Firebase.database.getReference(Const.TEST_DB_KEY)

    private val _stageInfo = MutableStateFlow<StageInfoState?>(null)
    private val _currentTime = MutableStateFlow("00:00.00")
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val list = listOf(
        FirebaseStatus(participantID = 792, UserStatus.RIDES),
        FirebaseStatus(participantID = 793, UserStatus.WAITING),
        FirebaseStatus(participantID = 794, UserStatus.HEATING),
        FirebaseStatus(participantID = 795, UserStatus.NEXT),
    )

    val uiState: StateFlow<StagesDetailsScreenState?> = combine(
        _stageInfo,
        _currentTime,
        _userMessage,
        _isLoading,
    ) { stageInfo, currentTime, userMessage, isLoading ->
        Log.e("aaaerror", "exception.message.toString()")
        StagesDetailsScreenState(
            stageInfo = stageInfo,
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

    fun openTimeMenu(participantID: Long) {
        _stageInfo.value?.let {
            _stageInfo.value = _stageInfo.value?.copy(results = it.results.map { user ->
                if (user.participantID == participantID && !user.openTimeMenu) {
                    user.copy(openTimeMenu = true)
                } else {
                    user.copy(openTimeMenu = false)
                }
            })
        }
    }

    fun postTime(requestBody: PostTimeRequestBody) {
        viewModelScope.launch(exceptionHandler) {
            if (gymkhanaCupRepository.postTime(requestBody).isSuccessful) {
                _stageInfo.value?.stageID?.let {
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
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                val request = Request.Builder().url("http://${Const.controllerIp}/time").build()
                val response = client.newCall(request).execute()
                val time = response.getResult().toDouble().millisecondToMinutes()
                _currentTime.value = time
            }
        }
    }

    private fun getCurrentTimeMok() {
        viewModelScope.launch {
            repeat(1000) {
                delay(1000)
                _currentTime.value = "00:${Random.nextInt(10, 60)}.${Random.nextInt(10, 60)}"
            }
        }
    }

    fun getStageInfo(id: String, type: String) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stageInfo.value = gymkhanaCupRepository.getStageInfo(id, type).toStageInfoState(list)
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