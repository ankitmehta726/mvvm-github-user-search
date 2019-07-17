package com.gihub.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by ankitmehta726
 */

@Singleton
class AppViewModelFactory
@Inject
constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        /*Log.d("ViewModelFactory",""+creators.size)*/
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}