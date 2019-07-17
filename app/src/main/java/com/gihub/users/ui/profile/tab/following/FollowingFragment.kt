package com.gihub.users.ui.profile.tab.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gihub.users.R
import com.gihub.users.data.model.Follower
import com.gihub.users.data.model.SearchedUser
import com.gihub.users.ui.base.BaseFragment
import com.gihub.users.ui.profile.tab.FollowerAdapter
import com.gihub.users.ui.search.MainActivity
import com.gihub.users.viewmodel.AppViewModelFactory
import com.gihub.users.widgets.NetworkUtils
import kotlinx.android.synthetic.main.fragment_followers.*
import javax.inject.Inject

/**
 * Created By ankitmehta726
 */

class FollowingFragment : BaseFragment<FollowingViewModel>() {

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory

    private lateinit var followingViewModel: FollowingViewModel


    private lateinit var followingListAdapter: FollowerAdapter
    private lateinit var layoutManager1: LinearLayoutManager
    private lateinit var followingList: ArrayList<Follower>

    private lateinit var currentUser: SearchedUser

    companion object {
        private const val CURRENT_USER = "current_user"

        fun newInstance(currentUser: SearchedUser): FollowingFragment {
            val args: Bundle = Bundle()
            args.putParcelable(CURRENT_USER, currentUser)
            val fragment = FollowingFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser = arguments?.getParcelable(CURRENT_USER)!!

        followingList = ArrayList()
        observeFollowingList()

        if (NetworkUtils.isNetworkConnected(activity as MainActivity))
            followingViewModel.getFollowing(currentUser.login)
    }

    override fun getViewModel(): FollowingViewModel {
        followingViewModel = ViewModelProviders.of(this@FollowingFragment, appViewModelFactory)
            .get(FollowingViewModel::class.java)
        return followingViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingListAdapter = FollowerAdapter(activity as MainActivity, followingList)

        layoutManager1 = LinearLayoutManager(activity as MainActivity)

        rvFollowers.apply {
            layoutManager = layoutManager1
            adapter = followingListAdapter
        }
    }

    private fun observeFollowingList() {
        followingViewModel.followingList.observe(this, Observer {
            followingList.clear()
            followingList.addAll(it!!)
            followingListAdapter.notifyDataSetChanged()
        })
    }
}