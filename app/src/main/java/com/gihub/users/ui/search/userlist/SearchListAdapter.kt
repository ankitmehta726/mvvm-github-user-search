package com.gihub.users.ui.search.userlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.gihub.users.R
import com.gihub.users.data.model.SearchedUser
import kotlinx.android.synthetic.main.item_searched_user.view.*

/**
 * Created By ankitmehta726
 */

class SearchListAdapter(val context: Context, private  var userList: ArrayList<SearchedUser>):
    RecyclerView.Adapter<SearchListAdapter.SearchedUserViewHolder>(){

    private lateinit var onUserClickListener: OnUserClickListener

    fun setOnUserClickListener(onUserClickListener: OnUserClickListener){
        this.onUserClickListener = onUserClickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchedUserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_searched_user, p0, false)
        return SearchedUserViewHolder(view)
    }

    override fun getItemCount() = userList.size /*5*/

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {
        Glide.with(context)
            .load(userList[position].avatarUrl)
            .into(holder.ivUserAvatar)

        holder.tvUserName.text = userList[position].login

        holder.cvUserCard.setOnClickListener {
            onUserClickListener.onUserClick(userList[position])
        }
    }


    class SearchedUserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ivUserAvatar = view.civUserAvatar!!
        val tvUserName = view.tvLoginId!!
        val cvUserCard = view.cvUserCard!!
    }

    interface OnUserClickListener{
        fun onUserClick(searchedUser: SearchedUser)
    }
}