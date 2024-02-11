package com.example.motogymkhana.screens.stagedetails

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.utils.WhileUiSubscribed
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.mappers.toStageInfoState
import com.example.motogymkhana.model.FirebaseStatus
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.StageInfoState
import com.example.motogymkhana.model.UserStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StageDetailsViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository
) : ViewModel() {

    private val myRef = Firebase.database.getReference(Const.TEST_DB_KEY)

    private val _stageInfo = MutableStateFlow<StageInfoState?>(null)
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val list = listOf(
        FirebaseStatus(userId = 1585, UserStatus.RIDES),
        FirebaseStatus(userId = 3015, UserStatus.WAITING),
        FirebaseStatus(userId = 3019, UserStatus.HEATING),
        FirebaseStatus(userId = 2484, UserStatus.NEXT),
        FirebaseStatus(userId = 3220, UserStatus.RIDES),
    )

    val uiState: StateFlow<StagesDetailsScreenState?> = combine(
        _stageInfo,
        _userMessage,
        _isLoading,
    ) { stageInfo, userMessage, isLoading->
        StagesDetailsScreenState(
            stageInfo  = stageInfo,
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
        Log.e("aaaerror",exception.message.toString())
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    init {
        //saveData()

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var str = ""
                val size = snapshot.children.toList().size
                snapshot.children.forEach {
                    Log.e("aaa"," snapshot.children.forEach")

                    val firebaseStatus = it.getValue(FirebaseStatus::class.java)

                    firebaseStatus?.let {
                        Log.e("aaa",it.userId.toString())
                        Log.e("aaa",it.userStatus.toString())
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage(R.string.error)
            }
        }



        myRef.addValueEventListener(listener)
    }

    private fun saveData(){
        myRef.push().updateChildren(mapOf("111" to FirebaseStatus(userId = 1585, UserStatus.RIDES)))
//        myRef.push().setValue(Test(testString = binding.textView.text.toString()))
//        myRef.push().setValue(Test(testString = "TEXT"))
    }

//        private fun saveData(){
//        myRef.push().setValue(Test(testString = binding.textView.text.toString()))
//            myRef.push().updateChildren(mapOf("111" to Test(Random.nextInt(1000), "1-1")))
//        myRef.push().setValue(Test(testString = "TEXT"))
//        }


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

data class Test(
    val testInt: Int = 12,
    val testString: String = "str"
)