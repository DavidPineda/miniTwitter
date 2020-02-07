package com.example.minitwitter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minitwitter.common.Constants
import com.example.minitwitter.common.SharedPreferencesManager
import com.example.minitwitter.models.response.Like
import com.example.minitwitter.models.response.Tweet
import kotlinx.android.synthetic.main.fragment_tweet.view.*

class MyTweetRecyclerViewAdapter(private var ctx: Context, private var mValues: List<Tweet>) : RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_tweet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Tweet = mValues[position]

        holder.tvUserName.text = item.user.username
        holder.tvMessage.text = item.mensaje
        holder.tvLikesCount.text = item.likes.size.toString()

        if(item.user.photoUrl.isNotEmpty()) {
            Glide.with(ctx)
                .load("https://www.minitwetter.com/apiv1/uploads/photos" + item.user.photoUrl)
                .into(holder.ivAvatar)
        }

        val userName: String = SharedPreferencesManager.getSomeStringValue(Constants.PREF_USERNAME)
        for(like in item.likes) {
            if(like.username == userName) {
                Glide.with(ctx)
                    .load(R.drawable.ic_like_pink)
                    .into(holder.ivLike)
                holder.tvLikesCount.setTextColor(ctx.resources.getColor(R.color.pink))
                holder.tvLikesCount.setTypeface(null, Typeface.BOLD)
                break
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvUserName: TextView = mView.tvUserName
        val tvMessage: TextView = mView.tvMessage
        val tvLikesCount: TextView = mView.tvLikesCount
        val ivAvatar: ImageView = mView.ivAvatar
        val ivLike: ImageView = mView.ivLike

        override fun toString(): String {
            return super.toString() + " '" + tvUserName.text + "'"
        }
    }
}
