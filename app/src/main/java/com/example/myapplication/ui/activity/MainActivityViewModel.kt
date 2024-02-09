package com.example.myapplication.ui.activity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.data.repo.TheRepository
import com.example.myapplication.event.SingleLiveEvent
import com.example.myapplication.ui.callback.PetListCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class MainActivityViewModel(
    private val repository: TheRepository,
) : ViewModel(), PetListCallback {

    /* Observable fields for UI */
    val isLoading = ObservableBoolean(false)
    val itemsList: ObservableList<AnimalData> = ObservableArrayList()
    val isShowNoResults = ObservableBoolean(false)

    /* Observable fields for activity */
    val openLoginScreen = SingleLiveEvent<Boolean>()
    val onError = SingleLiveEvent<Throwable>()
    val openDetailsScreen = SingleLiveEvent<AnimalData>()

    /* Local fields */
    private val compositeDisposable = CompositeDisposable()
    private val locationInputChange: PublishSubject<String> = PublishSubject.create()
    private var currentPageNo = 1
    private var hasNextPage = true
    private var currentLocationInput: String? = null

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
        locationInputChange
            .subscribeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { query ->
                    when {
                        query.isEmpty() -> fetchList(pageNo = 1)
                        query.length > 1 -> fetchList(pageNo = 1, query)
                        else -> {
                            //ignore
                        }
                    }
                },
                { throwable -> onError.value = throwable }
            )
            .also { compositeDisposable.add(it) }
    }

    private fun fetchList(pageNo: Int, userLocationInput: String? = null) {
        repository.getAnimals(pageNo = pageNo, userLocation = userLocationInput)
            .doOnSubscribe { isLoading.set(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> onPageFetchSuccess(response) },
                { throwable ->
                    this.isLoading.set(false)
                    if (userLocationInput == null) {
                        //ignore errors from location search
                        onError.value = throwable
                    } else {
                        //if error while location search show empty list
                        onPageFetchSuccess(animalsReponse = AnimalsList())
                    }
                }
            ).also { compositeDisposable.add(it) }
    }

    private fun onPageFetchSuccess(animalsReponse: AnimalsList) {
        this.isLoading.set(false)
        this.currentPageNo = animalsReponse.currentPage
        this.hasNextPage = animalsReponse.hasNextPage
        if (currentPageNo == 1) {
            itemsList.clear()
            isShowNoResults.set(animalsReponse.animals.isEmpty())
        }
        itemsList.addAll(animalsReponse.animals)
    }

    override fun onPetItemClick(animalData: AnimalData) {
        openDetailsScreen.value = animalData
    }

    override fun onLoadNextPage() {
        if (hasNextPage && isLoading.get().not()) {
            fetchList(
                pageNo = ++currentPageNo,
                userLocationInput = if ((currentLocationInput?.length
                        ?: 0) > 1
                ) currentLocationInput else null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onLocationInputChange(s: CharSequence, start: Int, before: Int, count: Int) {
        currentLocationInput = s.toString()
        locationInputChange.onNext(currentLocationInput)
    }

}