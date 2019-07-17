package com.gihub.users.di.module

import android.app.Application
import android.content.Context
import com.gihub.users.data.AppDataManager
import com.gihub.users.data.IAppDataSource
import com.gihub.users.data.remote.IRemoteSource
import com.gihub.users.data.remote.RemoteDataManager
import com.gihub.users.di.module.search.SearchFragmentsBindingModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelBindingModule::class, SearchFragmentsBindingModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun getRemoteSource(remoteDataManager: RemoteDataManager): IRemoteSource = remoteDataManager

    @Provides
    @Singleton
    internal fun provideDataManger(appDataManager: AppDataManager): IAppDataSource = appDataManager
}