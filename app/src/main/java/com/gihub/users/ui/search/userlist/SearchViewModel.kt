package com.gihub.users.ui.search.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gihub.users.R
import com.gihub.users.data.AppDataManager
import com.gihub.users.data.model.SearchResultResponse
import com.gihub.users.data.model.SearchedUser
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel
@Inject
constructor(var appDataManager: AppDataManager) : ViewModel() {

    var loading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var searchedUsersList = MutableLiveData<List<SearchedUser>>()

    private val compositeDisposable = CompositeDisposable()

    //Search would be performed by calling the method from AppDataManager.
    fun searchUsers(query: String, pageNumber: Int) {
        loading.value = true
        val searchDisposable = appDataManager.searchUsers(query, pageNumber.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<SearchResultResponse>>() {
                override fun onSuccess(response: Response<SearchResultResponse>) {
                    loading.value = false
                    if (response.isSuccessful && response.code() == 200) {
                        if (response.body()?.userList != null /*&&
                            (response.body()?.userList as List).isNotEmpty()*/){
                            searchedUsersList.value = response?.body()?.userList as ArrayList<SearchedUser>
                        }
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onError(throwable: Throwable) {
                    loading.value = false
                    if (throwable.message != null)
                        message.value = throwable.message
                    /*else
                        message.value = context.getString(R.string.unable_to_fetch_restaurants)*/
                }
            })
        compositeDisposable.add(searchDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}