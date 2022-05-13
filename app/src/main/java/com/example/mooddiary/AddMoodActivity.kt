package com.example.myapp01

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_add_mood.*
import java.util.*

class AddMoodActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    //declare calendar time
    var mood_day = 0
    var mood_month = 0
    var mood_year = 0
    var mood_hour = 0
    var mood_minute = 0

    var mood_Day = 0
    var mood_Month = 0
    var mood_Year = 0
    var mood_Hour = 0
    var mood_Minute = 0

    lateinit var maindrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mood)

        //add spinner content
        maindrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val moodList_spinner = mutableListOf<String>()
        moodList_spinner.add("delight")
        moodList_spinner.add("joy")
        moodList_spinner.add("fear")
        moodList_spinner.add("worried")
        moodList_spinner.add("happy")
        moodList_spinner.add("surprised")
        moodList_spinner.add("nervous")
        moodList_spinner.add("sad")
        moodList_spinner.add("angry")
        moodList_spinner.add("disgust")

        //list adapter
        val mood_adapter =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                moodList_spinner
            )

        findViewById<Spinner>(R.id.moodchoose1_spinner).adapter = mood_adapter
    }

    //save button onClick
    fun saveMoodTodayOnclick(view: View) {
        if (daytime_textView.text.isEmpty()) {
            Toast.makeText(this, "Please choose a time", Toast.LENGTH_SHORT).show()
        } else if (daytime_textView.text.isNotEmpty()) {
            val intent_jumptolist = Intent()
            intent_jumptolist.putExtra(
                "time_record",
                findViewById<TextView>(R.id.daytime_textView).text.toString()
            )
            intent_jumptolist.putExtra(
                "mood_record",
                findViewById<Spinner>(R.id.moodchoose1_spinner).selectedItem.toString()
            )
            setResult(RESULT_OK, intent_jumptolist)
            finish()
        }
    }

    //show the dialog to choose time
    fun addtimeDiag(view: View) {
        val c = Calendar.getInstance()
        mood_day = c.get(Calendar.DAY_OF_MONTH)
        mood_month = c.get(Calendar.MONTH)
        mood_year = c.get(Calendar.YEAR)
        mood_hour = c.get(Calendar.HOUR)
        mood_minute = c.get(Calendar.MINUTE)

        DatePickerDialog(this, this, mood_year, mood_month, mood_day).show()
    }


    //set Date
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        mood_Day = dayofmonth
        mood_Month = month
        mood_Year = year

        val c = Calendar.getInstance()
        mood_hour = c.get(Calendar.HOUR)
        mood_minute = c.get(Calendar.MINUTE)

        TimePickerDialog(this, this, mood_hour, mood_minute, true).show()
    }

    //set time
    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hourofday: Int, minute: Int) {
        mood_Hour = hourofday
        mood_Minute = minute

        findViewById<TextView>(R.id.daytime_textView).text =
            "$mood_Month/$mood_Day/$mood_Year ${String.format("%02d:%02d", mood_Hour, mood_Minute)}"
    }

    //navigation drawer onClick
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
        redirectActivity(MainActivity());
    }

    fun quoteOnclick(view: View) {
        redirectActivity(QuotesActivity());
    }

    //add diary
    fun diaryOnclick(view: View) {
        Toast.makeText(this, "todo: diary", Toast.LENGTH_SHORT).show()
    }

    private fun redirectActivity(activity: Activity) {
        var intent = Intent(this, activity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(maindrawerLayout)
    }


}