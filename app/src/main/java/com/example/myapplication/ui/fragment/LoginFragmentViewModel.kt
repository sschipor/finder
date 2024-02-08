package com.example.myapplication.ui.fragment

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.repo.TheRepository
import com.example.myapplication.event.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class LoginFragmentViewModel(
    app: Application,
    private val repository: TheRepository,
) : AndroidViewModel(app) {

    /* Observable fields for UI */
    val isLoading = ObservableBoolean(false)

    /* Observable fields for fragment */
    val onLoginSuccess = SingleLiveEvent<Boolean>()
    val onLoginError = SingleLiveEvent<Throwable>()

    /* Local fields */
    private var loginDisposable: Disposable? = null

    fun onDoLogin() {
        if (loginDisposable == null || loginDisposable?.isDisposed == true) {
            repository.loginUser()
                .doOnSubscribe { isLoading.set(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        isLoading.set(false)
                        onLoginSuccess.value = true
                    },
                    { throwable ->
                        isLoading.set(false)
                        onLoginError.value = throwable
                    }
                )
                .also { loginDisposable = it }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loginDisposable?.dispose()
        loginDisposable = null
    }
}