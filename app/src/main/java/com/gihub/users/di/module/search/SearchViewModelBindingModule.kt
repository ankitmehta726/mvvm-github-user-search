package com.gihub.users.di.module.search

import androidx.lifecycle.ViewModel
import com.gihub.users.di.key.ViewModelKey
import com.gihub.users.ui.profile.UserProfileViewModel
import com.gihub.users.ui.search.userlist.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindUserListViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileViewModel(userProfileViewModel: UserProfileViewModel): ViewModel
}