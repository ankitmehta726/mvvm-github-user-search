package com.gihub.users

import android.app.Activity
import android.app.Application
import com.gihub.users.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class GithubApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}