package com.gihub.users.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gihub.users.di.key.ViewModelKey
import com.gihub.users.di.module.search.SearchViewModelBindingModule
import com.gihub.users.ui.profile.tab.follower.FollowerViewModel
import com.gihub.users.ui.profile.tab.following.FollowingViewModel
import com.gihub.users.ui.search.MainViewModel
import com.gihub.users.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [SearchViewModelBindingModule::class])
abstract class ViewModelBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FollowerViewModel::class)
    abstract fun bindFollowerViewModel(followerViewModel: FollowerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FollowingViewModel::class)
    abstract fun bindFollowingViewModel(followingViewModel: FollowingViewModel): ViewModel
}