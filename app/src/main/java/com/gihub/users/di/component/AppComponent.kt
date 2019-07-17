package com.gihub.users.di.component

import android.app.Application
import com.gihub.users.GithubApp
import com.gihub.users.di.module.ActivityBindingModule
import com.gihub.users.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class, ActivityBindingModule::class]
)
interface AppComponent: AndroidInjector<GithubApp> {

    @Component.Builder
     interface  Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: GithubApp?)
}