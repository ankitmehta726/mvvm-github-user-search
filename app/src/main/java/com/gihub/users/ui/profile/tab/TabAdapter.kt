package com.gihub.users.ui.profile.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gihub.users.data.model.SearchedUser
import com.gihub.users.ui.profile.tab.follower.FollowersFragment
import com.gihub.users.ui.profile.tab.following.FollowingFragment

/**
 * Created By ankitmehta726
 */

class TabAdapter(fm: FragmentManager, var titles: List<String>, var searchedUser: SearchedUser,
                 BEHAVIOR_SET_USER_VISIBLE_HINT: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment.newInstance(searchedUser)
            1 -> FollowingFragment.newInstance(searchedUser)
            else -> FollowersFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                return "Followers  (${titles[0]})"
            }

            1 -> {
                return "Following  (${titles[1]})"
            }

            else -> "Followers  (${titles[0]})"
        }
    }
}