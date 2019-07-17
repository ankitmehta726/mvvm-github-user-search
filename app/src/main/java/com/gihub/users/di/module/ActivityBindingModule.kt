package com.gihub.users.di.module

import com.gihub.users.di.scope.ActivityScoped
import com.gihub.users.ui.search.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    @ActivityScoped
    abstract fun mainActivity(): MainActivity
}