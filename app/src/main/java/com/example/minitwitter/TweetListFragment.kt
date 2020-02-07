package com.example.minitwitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minitwitter.models.response.Tweet
import com.example.minitwitter.services.AuthApiClient
import com.example.minitwitter.services.AuthApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetListFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1
    private lateinit var tweetList: List<Tweet>
    private lateinit var authApiService: AuthApiService
    private lateinit var authApiClient: AuthApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tweet_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                retrofitInit()


                val call: Call<List<Tweet>> = authApiService.getAllTweets()
                call.enqueue(object : Callback<List<Tweet>> {
                    override fun onFailure(call: Call<List<Tweet>>?, t: Throwable?) {
                        Toast.makeText(this@TweetListFragment.context, "Wireless error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                        if (response.isSuccessful) {
                            tweetList = response.body()!!
                            adapter = this@TweetListFragment.context?.let { MyTweetRecyclerViewAdapter(it, tweetList) }
                        } else {
                            Toast.makeText(this@TweetListFragment.context, "Data Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
        return view
    }

    private fun retrofitInit() {
        authApiClient = AuthApiClient.instance!!
        authApiService = authApiClient.authApiService
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                TweetListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
