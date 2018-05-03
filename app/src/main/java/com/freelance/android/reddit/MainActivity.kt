package com.freelance.android.reddit

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.freelance.android.reddit.adapter.RedditAdapter
import com.freelance.android.reddit.model.RedditModel
import com.freelance.android.reddit.service.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.getName()

    private var disposable: Disposable? = null

    private val apiserve by lazy {
        Log.i(LOG_TAG, "TEST: apiserve() is called...")

        APIService.create()
    }

    private var mAdapter: RedditAdapter? = null

    private var mParentData: RedditModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = this.findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle("Reddit News")
        toolbar.setTitleTextColor(resources.getColor(R.color.orange))

        initView()
    }

    private fun initView() {
        Log.i(LOG_TAG, "TEST: initView() is called...")

        val rvNewsList = this.findViewById(R.id.rvNewsList) as RecyclerView
        rvNewsList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        loadJSON()
    }

    private fun loadJSON() {
        Log.i(LOG_TAG, "TEST: loadJSON() is called...")

        progress.visibility = View.VISIBLE

        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {

             disposable = apiserve.fetchRedditNews(10)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(
                               {
                                   //result -> displayResult(result)
                                   result -> mParentData = result
                                   mAdapter = RedditAdapter(this, mParentData!!)
                                   rvNewsList.adapter = mAdapter
                                   progress.visibility = View.INVISIBLE
                               },
                               { error ->
                                   Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                   progress.visibility = View.INVISIBLE
                               }
                       )

        } else {
            progress.visibility = View.INVISIBLE
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun displayResult(result: RedditModel?) {
        Log.i(LOG_TAG, "TEST: displayResult() is called...")

        mParentData = result
        mAdapter = RedditAdapter(this, mParentData!!)
        rvNewsList.adapter = mAdapter
        progress.visibility = View.INVISIBLE
    }*/

    override fun onPause() {
        Log.i(LOG_TAG, "TEST: onPause() is called...")

        super.onPause()
        disposable?.dispose()
        //mCompositeDisposable?.dispose()
    }

    override fun onDestroy() {
        Log.i(LOG_TAG, "TEST: onDestroy() is called...")

        super.onDestroy()
        disposable?.dispose()
    }
}
