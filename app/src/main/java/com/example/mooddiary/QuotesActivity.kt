package com.example.myapp01

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp01.Adapter.QuoteAdapter
import kotlinx.android.synthetic.main.activity_quotes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesActivity : AppCompatActivity() {
    private val BASE_URL = "https://api.quotable.io/search/quotes/"
    private val TAG = "QuotesActivity"

    lateinit var maindrawerLayout: DrawerLayout
    val quotesList = ArrayList<Quote>()
    val quote_adpater = QuoteAdapter(quotesList)
    var search_term_default = "life"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        val quote_recycler_view = findViewById<RecyclerView>(R.id.quotes_recyclerview)
        quote_recycler_view.adapter = quote_adpater
        quote_recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getdata_quote(search_term_default)
        findViewById<EditText>(R.id.searchterm_edittext).hideKeyboard()
        maindrawerLayout = findViewById(R.id.drawer_layout)
    }


    //get quotes data using api
    fun getdata_quote(search_term: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val searchQuoteAPI = retrofit.create(SearchQuoteInterface::class.java)
        searchQuoteAPI.searchquote(search_term)
            .enqueue(object : Callback<QuoteData> {
                override fun onFailure(call: Call<QuoteData>, t: Throwable) {
                    Log.d(TAG, "onFailure:$t")
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<QuoteData>, response: Response<QuoteData>) {
                    Log.d(TAG, "onResponse: $response")

                    val body = response.body()
                    Log.d(TAG, "wait for result")
                    if (body == null) {
                        diag_showmessage("No Results", "Please try another one")
                        Log.d(TAG, "No result")
                        getdata_quote(search_term)
                        return
                    }

                    if (body.results.isEmpty()) {
                        diag_showmessage("No Results", "Please try another one")
                        Log.d(TAG, "No result")

                        getdata_quote(search_term_default)
                    }

                    quotesList.addAll(body.results)
                    quote_adpater.notifyDataSetChanged()
                }
            })
    }

    fun diag_showmessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        //title
        builder.setTitle(title)
        //message
        builder.setMessage(message)
        // Set an icon, optional
        builder.setIcon(android.R.drawable.ic_delete)
        // Set the button actions (i.e. listeners), optional
        builder.setPositiveButton("Okay") { dialog, which ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    //search button
    fun search_quoteBtnOnclick(view: View) {
        quotesList.clear()
        quotes_recyclerview.scrollToPosition(0)
        if (findViewById<EditText>(R.id.searchterm_edittext).text.isNotEmpty()) {
            val text_in_quoteEditText =
                findViewById<EditText>(R.id.searchterm_edittext).text.toString()
            getdata_quote(text_in_quoteEditText)

        } else {
            getdata_quote(search_term_default)
        }
        findViewById<EditText>(R.id.searchterm_edittext).text.clear()
    }

    /*Reference:
    *navigation:activities(java)
    * https://www.youtube.com/watch?v=iesMhKUtYT8&list=PLkbnctMtUcaT1he4QO6Oqfth3cRoEOQUJ&index=4&t=1427s
    * */

    fun menuOnclick(view: View) {
        openDrawer(maindrawerLayout)
    }

    fun openDrawer(maindrawerLayout: DrawerLayout) {
        maindrawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeClick(view: View) {
        closeDrawer(maindrawerLayout)
    }

    fun closeDrawer(maindrawerLayout: DrawerLayout) {
        if (maindrawerLayout.isDrawerOpen(GravityCompat.START)) {
            maindrawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun homeOnclick(view: View) {
        redirectActivity(MainActivity())
    }

    fun quoteOnclick(view: View) {
        redirectActivity(QuotesActivity())
    }

    //add diary
    fun diaryOnclick(view: View) {
        redirectActivity(Diary())
    }

    private fun redirectActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(maindrawerLayout)
    }

    //hide keyboard when it loses focus
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


}