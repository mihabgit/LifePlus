package com.dewdrops.lifeplus.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.dewdrops.lifeplus.R
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dewdrops.lifeplus.datasource.model.Show
import com.dewdrops.lifeplus.datasource.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import com.dewdrops.lifeplus.adapter.ShowsAdapter


class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: ShowsAdapter? = null
    private var tvShowList: List<Show>? = null
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LifePlus)
        setContentView(R.layout.activity_dashboard)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val tvProfile = findViewById<TextView>(R.id.tvProfile)
        tvProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        recyclerView = findViewById(R.id.rvTvShows)
        searchView = findViewById(R.id.searchView)

        tvShowList = ArrayList()

        searchView.queryHint = "Search from here..."
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    val call: Call<List<Show>> = RetrofitInstance.api.getSearchResult(newText)
                    call.enqueue(object : Callback<List<Show>> {
                        override fun onResponse(
                            call: Call<List<Show>>,
                            response: Response<List<Show>>
                        ) {

                            response.body()?.let { generateDataList(it) }
                            println("babu url : " + response.raw().request().url())
                        }

                        override fun onFailure(call: Call<List<Show>>, t: Throwable?) {

                            Toast.makeText(
                                this@DashboardActivity,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                    return false
                }
            })

    }

    private fun generateDataList(list: List<Show>) {
        tvShowList = list
        adapter = ShowsAdapter(this, tvShowList as ArrayList<Show>)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter?.setOnItemClickListener(object : ShowsAdapter.OnItemClickListener {
            override fun onItemClickListener(tvShow: Show) {
                showDialog(tvShow)
            }
        })
    }


    private fun showDialog(tvShow: Show) {
        val dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.show()

        val tvShowName = dialog.findViewById<TextView>(R.id.tvShowName)
        val tvShowType = dialog.findViewById<TextView>(R.id.tvShowType)
        val tvShowLanguage = dialog.findViewById<TextView>(R.id.tvShowLanguage)

        tvShowName.text = "Name : "+tvShow.show.name
        tvShowType.text = "Type : "+tvShow.show.type
        tvShowLanguage.text = "Language : "+tvShow.show.language

        val yesBtn = dialog.findViewById(R.id.btnOk) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }

    }
}