package com.example.myapp01

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.myapp01.Adapter.DiaryAdapter
import com.example.myapp01.Database.DiaryEntity
import com.google.gson.GsonBuilder


import kotlinx.android.synthetic.main.activity_diary.*
import okhttp3.*
import java.io.IOException

class Diary : AppCompatActivity(), DiaryAdapter.RowClickListener {
    //lateint variables used
    lateinit var diaryAdapter: DiaryAdapter
    lateinit var viewModel: DiaryViewModel
    lateinit var maindrawerLayout: DrawerLayout

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        maindrawerLayout = findViewById(R.id.drawer_layout)

        //apply recycler view
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Diary)
            diaryAdapter = DiaryAdapter(this@Diary)
            adapter = diaryAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        viewModel.diaryObserver().observe(this, Observer {
            diaryAdapter.setListData(it as ArrayList<DiaryEntity>)
            diaryAdapter.notifyDataSetChanged() //notify the changes to adapter
        })

        //setOnClickListener for button
        saveButton.setOnClickListener {
            val title = diary_title.text.toString()
            diary_title.hideKeyboard() //hide keyboard once once button is hit
            val content = diary_content.text.toString()
            diary_content.hideKeyboard() //hide keyboard once button is hit

            if (title.isNotEmpty() && content.isNotEmpty()) { //checks if empty
                if (saveButton.text.equals("POST")) {
                    val diaryInfo = DiaryEntity(0, title, content)
                    viewModel.diaryInsert(diaryInfo) // inserts diary

                } else {
                    val diaryInfo = DiaryEntity(
                        diary_title.getTag(diary_title.id).toString().toInt(),
                        title,
                        content
                    )

                    saveButton.setText("POST")
                    updateDialog(diaryInfo)// call update dialog
                }
            } else {
                Toast.makeText(this, "Diary is not completed", Toast.LENGTH_SHORT)
                    .show() //toast message shown when diary not completed
            }
            diary_title.text.clear() //clear title text once posted
            diary_content.text.clear() //clear content text once posted
        }
    }

    //function for deleting,when image is clicked diary will be deleted
    override fun onDeleteClickListener(diary: DiaryEntity) {
        deleteDialog(diary)
    }

    //onItemClickListener function, when click on a diary text
    @SuppressLint("SetTextI18n")
    override fun onItemClickListener(diary: DiaryEntity) {
        diary_title.setText(diary.title)
        diary_content.setText(diary.content)
        diary_title.setTag(diary_title.id, diary.id)
        saveButton.text = "Update" //change text to update
    }

    override fun onAnalyseClickListener(diary: DiaryEntity) {
        Log.d("Diary", "Diary analysis")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://twinword-emotion-analysis-v1.p.rapidapi.com/analyze/?text=${diary.content}")
            .get()
            .addHeader("X-RapidAPI-Host", "twinword-emotion-analysis-v1.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "994bdfbf94msh3b119a9d7f448adp13da27jsnb83b6a87ab9a")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Diary", "on failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                if (body != null) {
                    println(body)
                    val gson = GsonBuilder().create()
                    val emotionDetail = gson.fromJson(body, EmotionData::class.java)
                    runOnUiThread {
                        Log.i("Diary", "runOnUiThread")
                        showEmotionDeatail(emotionDetail)
                    }
                }
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun showEmotionDeatail(emotionDetail: EmotionData) {
        val view = View.inflate(this, R.layout.emotion_detail_card, null)
        val disgust = view.findViewById<TextView>(R.id.disgust_text)
        val sadness = view.findViewById<TextView>(R.id.sadness_text)
        val anger = view.findViewById<TextView>(R.id.anger_text)
        val joy = view.findViewById<TextView>(R.id.joy_text)
        val surprise = view.findViewById<TextView>(R.id.surprise_text)
        val fear = view.findViewById<TextView>(R.id.fear_text)
        val pieChartEmotion = view.findViewById<AnyChartView>(R.id.pie_chart_emotion)

        var disgustText = "disgust:0"
        var sadnessText = "sadness:0"
        var angerText = "anger:0"
        var joyText = "joy:0"
        var surpriseText = "surprise:0"
        var fearText = "fear:0"


        val emo_dis = emotionDetail.emotion_scores.disgust
        val emo_sad = emotionDetail.emotion_scores.sadness
        val emo_ang = emotionDetail.emotion_scores.anger
        val emo_joy = emotionDetail.emotion_scores.joy
        val emo_sur = emotionDetail.emotion_scores.surprise
        val emo_fear = emotionDetail.emotion_scores.fear

        val totalEmotion = emo_dis + emo_sad + emo_ang + emo_joy + emo_sur + emo_fear
        if (totalEmotion > 0) {
            disgustText = "disgust:${emo_dis / totalEmotion}"
            sadnessText = "sadness:${emo_sad / totalEmotion}"
            angerText = "anger:${emo_ang / totalEmotion}"
            joyText = "joy:${emo_joy / totalEmotion}"
            surpriseText = "surprise:${emo_sur / totalEmotion}"
            fearText = "fear:${emo_fear / totalEmotion}"
        }

        disgust.text = disgustText
        sadness.text = sadnessText
        anger.text = angerText
        joy.text = joyText
        surprise.text = surpriseText
        fear.text = fearText

        //        Pie chart:  https://www.youtube.com/watch?v=28IKmy-HCSk
        val emo_score = listOf(emo_dis, emo_sad, emo_ang, emo_joy, emo_sur, emo_fear)
        val emo_type = listOf("disgust", "sadness", "anger", "joy", "surprise", "fear")
        val pie: Pie = AnyChart.pie()
        val dataPieChart: MutableList<DataEntry> = mutableListOf()
        for (index in emo_score.indices) {
            dataPieChart.add(ValueDataEntry(emo_type.elementAt(index), emo_score.elementAt(index)))
        }
        pie.data(dataPieChart)
        pie.title("Emotion Detail")
        pieChartEmotion.setChart(pie)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.setOnClickListener {
            dialog.dismiss()
        }
    }

    //hide keyboard function that is called earlier in code
    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    //hide keyboard when it loses focus
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    //dialog message called in delete function
    fun deleteDialog(diary: DiaryEntity) {

        // Create an alertdialog builder object,
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete this entry?")
        builder.setMessage("Are you sure that you want to delete it, it will be gone forever?")
        // Sets icon
        builder.setIcon(android.R.drawable.ic_delete)

        builder.setPositiveButton("YES") { dialog, which ->
            viewModel.diaryDelete(diary) //deletes the diary
            diary_title.text.clear() //clear title text once deleted
            diary_content.text.clear() //clear content text once deleted
            saveButton.setText("POST") //sets button text back to post just in case you clicked on the row instead of the delete image
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT)
                .show() //toast message shown when diary is deleted
        }
        builder.setNegativeButton("NO") { dialog, which ->
            dialog.dismiss() // dialog dismissed when no is hit
        }
        builder.setNeutralButton("Cancel") { dialog, which ->
            dialog.dismiss() // dialog is dismissed when cancel is hit
        }
        // dialog is created and shown
        val dialog = builder.create()
        dialog.show()
    }

    //dialog message called in update function
    fun updateDialog(diary: DiaryEntity) {

        // Create an alertdialog builder object,
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update this entry?")
        builder.setMessage("Are you sure that you want to make these changes?")
        // Sets icon
        builder.setIcon(android.R.drawable.ic_delete)

        builder.setPositiveButton("YES") { dialog, which ->
            viewModel.diaryUpdate(diary) //update the diary
            diary_title.text.clear() //clear title text once updated
            diary_content.text.clear() //clear content text once updated
            saveButton.setText("POST") //sets button text back to post just in case you clicked on the row instead

        }
        builder.setNegativeButton("NO") { dialog, which ->
            dialog.dismiss() // dialog dismissed when no is hit
        }
        builder.setNeutralButton("Cancel") { dialog, which ->
            dialog.dismiss() // dialog is dismissed when cancel is hit
        }
        // dialog is created and shown
        val dialog = builder.create()
        dialog.show()
    }

    /*Reference:
   *navigation:activities(java)
   * https://www.youtube.com/watch?v=iesMhKUtYT8&list=PLkbnctMtUcaT1he4QO6Oqfth3cRoEOQUJ&index=4&t=1427s
   * */

    //menu on click
    fun menuOnclick(view: View) {
        openDrawer(maindrawerLayout)
    }

    //opens drawer
    fun openDrawer(maindrawerLayout: DrawerLayout) {
        maindrawerLayout.openDrawer(GravityCompat.START)
    }

    //close click
    fun closeClick(view: View) {
        closeDrawer(maindrawerLayout)
    }

    //closes drawer
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

    //redirects user
    private fun redirectActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(maindrawerLayout)
    }
}