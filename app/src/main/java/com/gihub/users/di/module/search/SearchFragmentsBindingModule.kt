package com.gihub.users.di.module.search

import com.gihub.users.ui.profile.UserProfileFragment
import com.gihub.users.ui.profile.tab.follower.FollowersFragment
import com.gihub.users.ui.profile.tab.following.FollowingFragment
import com.gihub.users.ui.search.userlist.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module

abstract class SearchFragmentsBindingModule {

    @ContributesAndroidInjector
    abstract fun userListFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun userProfileFragment(): UserProfileFragment

    @ContributesAndroidInjector
    abstract fun followersFragment(): FollowersFragment

    @ContributesAndroidInjector
    abstract  fun followingFragment(): FollowingFragment
}