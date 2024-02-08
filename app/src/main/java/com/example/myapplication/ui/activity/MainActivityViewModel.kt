package com.example.myapplication.ui.activity

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.data.repo.TheRepository
import com.example.myapplication.event.SingleLiveEvent
import com.example.myapplication.ui.callback.PetListCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivityViewModel(
    private val app: Application,
    private val repository: TheRepository,
) : AndroidViewModel(app), PetListCallback {

    /* Observable fields for UI */
    val isLoading = ObservableBoolean(false)
    val itemsList: ObservableList<AnimalData> = ObservableArrayList()

    /* Observable fields for activity */
    val openLoginScreen = SingleLiveEvent<Boolean>()
    val onError = SingleLiveEvent<Throwable>()

    /* Local fields */
    private val compositeDisposable = CompositeDisposable()
    private var currentPageNo = 1
    private var hasNextPage = true

    init {
        repository.isUserLoggedIn()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { isLoggedIn ->
                    if (isLoggedIn.not()) {
                        openLoginScreen.value = true
                    } else {
                        fetchList(pageNo = 1)
                    }
                },
                { throwable -> onError.value = throwable }
            ).also { compositeDisposable.add(it) }
    }

    private fun fetchList(pageNo: Int) {
        repository.getAnimals(pageNo = pageNo)
            .doOnSubscribe { isLoading.set(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> onPageFetchSuccess(response) },
                { throwable ->
                    this.isLoading.set(false)
                    onError.value = throwable
                }
            ).also { compositeDisposable.add(it) }
    }

    private fun onPageFetchSuccess(animalsReponse: AnimalsList) {
        this.isLoading.set(false)
        this.currentPageNo = animalsReponse.currentPage
        this.hasNextPage = animalsReponse.hasNextPage
        if (currentPageNo == 0) {
            itemsList.clear()
        }
        itemsList.addAll(animalsReponse.animals)
    }

    override fun onPetItemClick(animalData: AnimalData) {

    }

    override fun onLoadNextPage() {
        if (hasNextPage && isLoading.get().not()) {
            fetchList(pageNo = ++currentPageNo)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}