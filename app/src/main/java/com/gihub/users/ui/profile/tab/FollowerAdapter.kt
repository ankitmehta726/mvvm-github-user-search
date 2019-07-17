package com.gihub.users.ui.profile.tab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gihub.users.R
import com.gihub.users.data.model.Follower
import kotlinx.android.synthetic.main.item_searched_user.view.*

/**
 * Created By ankitmehta726
 */

class FollowerAdapter (val context: Context, private  var userList: ArrayList<Follower>):
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FollowerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_searched_user, p0, false)
        return FollowerViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        Glide.with(context)
            .load(userList[position].avatarUrl)
            .into(holder.ivUserAvatar)

        holder.tvUserName.text = userList[position].login
    }


    class FollowerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ivUserAvatar = view.civUserAvatar!!
        val tvUserName = view.tvLoginId!!
    }
}