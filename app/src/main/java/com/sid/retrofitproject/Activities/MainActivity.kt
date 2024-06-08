package com.sid.retrofitproject.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.retrofitproject.Adapters.CommentsAdapter
import com.sid.retrofitproject.DataClasses.Comments
import com.sid.retrofitproject.R
import com.sid.retrofitproject.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val listOfComments: ArrayList<Comments> = ArrayList()
    lateinit var recyclerViewAdapter: CommentsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        recyclerViewAdapter = CommentsAdapter(listOfComments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter

        getAllComments()
    }

    private fun getAllComments() {
        RetrofitInstance().api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                if (response.isSuccessful) {
                    response.body()?.let {
                        listOfComments.clear()
                        listOfComments.addAll(it)
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.i("CHECK COMMENTS", "onResponse: ${t.message}")
            }
        })
    }
}