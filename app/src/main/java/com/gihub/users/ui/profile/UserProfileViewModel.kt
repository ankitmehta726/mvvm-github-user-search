package com.gihub.users.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gihub.users.data.AppDataManager
import com.gihub.users.data.model.Follower
import com.gihub.users.data.model.SearchedUser
import com.gihub.users.data.model.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By ankitmehta726
 */

class UserProfileViewModel
@Inject
constructor(var appDataManager: AppDataManager) : ViewModel() {
    var loading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    var userProfile = MutableLiveData<User>()

    private val compositeDisposable = CompositeDisposable()

    fun getUserProfile(userId: String) {
        loading.value = true
        val searchDisposable = appDataManager.getUserProfile(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<User>>() {
                override fun onSuccess(response: Response<User>) {
                    loading.value = false
                    if (response.isSuccessful && response.code() == 200) {
                        /*if (response.body()?.userList != null &&
                            (response.body()?.userList as List).isNotEmpty()){
                            searchedUsersList.value = response?.body()?.userList as ArrayList<SearchedUser>
                        }*/
                        userProfile.value = response.body()
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