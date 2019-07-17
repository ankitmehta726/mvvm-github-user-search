package com.gihub.users.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.gihub.users.R
import com.gihub.users.data.model.SearchedUser
import com.gihub.users.ui.base.BaseFragment
import com.gihub.users.ui.profile.tab.TabAdapter
import com.gihub.users.ui.search.MainActivity
import com.gihub.users.ui.search.userlist.SearchFragment
import com.gihub.users.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject
import android.content.Intent
import com.gihub.users.widgets.NetworkUtils

/**
 * Created By ankitmehta726
 */

class UserProfileFragment : BaseFragment<UserProfileViewModel>() {

    private lateinit var userProfileViewModel: UserProfileViewModel

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory

     lateinit var currentUser: SearchedUser

    override fun getViewModel(): UserProfileViewModel {
        userProfileViewModel = ViewModelProviders.of(this@UserProfileFragment, appViewModelFactory)
            .get(UserProfileViewModel::class.java)
        return userProfileViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoading()
        observeMessage()
        observeUserProfile()

        currentUser = arguments?.getParcelable(SearchFragment.SEARCHED_USER)!!

        if (NetworkUtils.isNetworkConnected(activity as MainActivity)){
            userProfileViewModel.getUserProfile(currentUser.login)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackArrow.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }


        //Share intent for  sharing the profile of selecting user through
        // other system/external applications.
        ivShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out this Github profile at: https://github.com/" + currentUser.login
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    private  fun observeLoading(){
        userProfileViewModel.loading.observe(this, Observer {
            (activity as MainActivity).setProgressVisibility(it)
        })
    }

    private  fun observeMessage(){
        userProfileViewModel.message.observe(this, Observer {
            (activity as MainActivity).showInfoToast(it)
        })
    }

    private fun observeUserProfile() {
        userProfileViewModel.userProfile.observe(this, Observer {
            Glide.with(activity as MainActivity)
                .load(it.avatarUrl)
                .into(civProfileAvatar)

            tvUserName.text = it.name
            tvLocation.text = it.location
            tvNumberOfGists.text = it.publicGists.toString()
            tvNumberOfRepos.text = it.publicRepos.toString()

            val titleArray = listOf(it.followers.toString(), it.following.toString())
            // Create an adapter that knows which fragment should be shown on each page
            val adapter = TabAdapter(activity?.supportFragmentManager!!, titleArray, currentUser, 1)

            // Set the adapter onto the view pager
            viewPager.adapter = adapter

            // Give the TabLayout the ViewPager
            tabLayout.setupWithViewPager(viewPager)
        })
    }
}