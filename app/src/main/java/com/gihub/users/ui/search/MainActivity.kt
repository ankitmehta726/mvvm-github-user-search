package com.gihub.users.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gihub.users.R
import com.gihub.users.ui.base.BaseActivity
import com.gihub.users.viewmodel.AppViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    override fun getViewModel(): MainViewModel {
        mainViewModel = ViewModelProviders.of(this@MainActivity, appViewModelFactory)
            .get(MainViewModel::class.java)
        return mainViewModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment? ?: return
    }
}
