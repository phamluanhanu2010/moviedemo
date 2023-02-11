package com.pgbank.personal.bases

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import luanpv.example.movieapp.utils.BaseException
import luanpv.example.movieapp.utils.ErrorType
import luanpv.example.movieapp.utils.ServerResponseEntity
import luanpv.example.movieapp.utils.SingleLiveEvent
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException


abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val message = MutableLiveData<String>()
    val noInternetConnectionEvent = SingleLiveEvent<Unit>()


    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val baseException: BaseException
                when (throwable) {

                    is UnknownHostException -> {
                        noInternetConnectionEvent.call()
                        baseException = BaseException(ErrorType.NETWORK, null, null, "NoInternet")
                    }
                    is SocketTimeoutException -> {
                        baseException = BaseException(ErrorType.NETWORK, null, null, "Timeout")
                    }
                    is SSLPeerUnverifiedException -> {
                        baseException = BaseException(ErrorType.NETWORK, null, null, "-1")
                    }
                    else -> {
                        baseException = BaseException(ErrorType.NETWORK, null, null, "NoInternet")
                    }
                }

                withContext(Dispatchers.Main) {
                    onError(
                        baseException.ErrorCode,
                        baseException.serverErrorResponse
                    )
                }
            }
        }
    }

    open fun onError(code: String?, source: ServerResponseEntity?) {
        message.value = source?.ErrorMessage
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun launchSilent(block: suspend CoroutineScope.() -> Unit): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                Log.wtf("EX", e)
            }
            return@launch
        }
    }

    protected val dbIO = viewModelScope
}