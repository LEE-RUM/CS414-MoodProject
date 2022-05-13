package com.example.myapp01

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.moodcarddialog_layout.view.*

private val FILE_NAME = "moodListFile"//time and mood
val mymoodtodayList = ArrayList<String>()
lateinit var mymoodtodayAdapter: ArrayAdapter<String>
val MOOD_LIST = arrayListOf<String>(
    "delight", "joy", "fear", "worried", "happy", "surprised", "nervous",
    "sad", "angry", "disgust"
)
val Text_Collection_Suggest = mapOf<String, String>(
    "The soul of sweet delight, can never be defiled" to "William Black",
    "I am not going to get upset. I am not going to let people steal my joy" to "Joel Osteen",
    "Nothing in life is to be feared. It is only to be understood" to "Marie Curie",
    "Worrying is like walking around with an umbrella waiting for it to rain" to "Wiz Khalifa",
    "Be happy for this moment. This moment is your life" to "Omar Khayyam",
    "Surprise is the greatest gift which life can grant us" to "Boris Pasternak",
    "Don't try to be what you are not. If you are nervous, be nervous. If you are shy, be shy. It's cute" to "Adriana Lima",
    "Sadness flies away on the wings of time" to "Jean de La Fontaine",
    "How much more grievous are the consequences of anger than the causes of it." to "Marcus Aurelius",
    "Disgust is often more deeply buried than envy and anger, but it compounds and intensifies the other negative emotions." to "Martha Nussbaum"
)
val image_ForCardDiag = listOf(
    R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
    R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10
)

lateinit var media_player: MediaPlayer
var music_isreleased = true

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 100

    //    private lateinit var binding: ActivityMainBinding
    lateinit var maindrawerLayout: DrawerLayout
    val TAG1 = "Main Activity"

    //notification
    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private val NOTIFICATION_ID = 0

    //    lateinit var music_image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

//        music_image = view.findViewById(R.id.music_img)
        rotateAnimation()

        Log.d(TAG1, "oncreate")
        mediaplay()
//        media_player.stop()

        shownotification()
        val app_start_notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification")
            .setContentText("Mood Diary app is in use")
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID, app_start_notification)

        maindrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
//        setContentView(R.layout.activity_main)
        mymoodtodayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_expandable_list_item_1,
            mymoodtodayList
        )
        moodlist1.adapter = mymoodtodayAdapter
        moodlist1.setOnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            mymoodtodayList.removeAt(position)
            mymoodtodayAdapter.notifyDataSetChanged()
            moodProgressBar()
            if (mymoodtodayList.isNotEmpty()) {
                Toast.makeText(this, "Delete $selectedItem", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "All moods are deleted", Toast.LENGTH_SHORT).show()
            }
            return@setOnItemLongClickListener true
        }
        moodlist1.setOnItemClickListener { adapterView, view, i, l ->
            var text_in_moodlist = moodlist1.getItemAtPosition(i).toString().trim()

            val mood_title_in_list = text_in_moodlist.split("——").toTypedArray()[1]
//            Log.d(TAG1,mood_title_in_list)
            showmoodcards(mood_title_in_list)
        }

        loadReference()
//        moodProgressBar()
    }


    private fun rotateAnimation() {
        val rotateAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        music_img!!.startAnimation(rotateAnimation)
    }


    /*!use for loop*/
    private fun showmoodcards(mood_title: String) {
        val view = View.inflate(this, R.layout.moodcarddialog_layout, null)
        val imageMoodCard = view.findViewById<ImageView>(R.id.image_card)
        val text_mood_MoodCard = view.findViewById<TextView>(R.id.quotes_textview1)
        val text_author_MoodCard = view.findViewById<TextView>(R.id.author_textview1)
        var image_resouce_moodCard = R.drawable.flower
        var moodtext_moodCard = "Freedom is the right to live"
        var authortext_moodCard = "Epictetus"
        for (i in 0..9) {
            when (mood_title) {
                MOOD_LIST[i] -> {
                    image_resouce_moodCard = image_ForCardDiag[i]
                    moodtext_moodCard = Text_Collection_Suggest.keys.elementAt(i)
                    authortext_moodCard = Text_Collection_Suggest.getValue(moodtext_moodCard)
                }
            }
        }

        imageMoodCard.setImageResource(image_resouce_moodCard)
        text_mood_MoodCard.text = moodtext_moodCard
        text_author_MoodCard.text = authortext_moodCard

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.close_diag.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun onaddAnothermoodOnclick(view: View) {
        val myIntent_toaddmood = Intent(this, AddMoodActivity::class.java)
        startActivityForResult(myIntent_toaddmood, REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val moodRecord = data?.getStringExtra("mood_record")
            val timetoday = data?.getStringExtra("time_record")
            mymoodtodayList.add("${timetoday}" + "——" + "${moodRecord}")
            mymoodtodayAdapter.notifyDataSetChanged()
            moodProgressBar()
        }
    }

    //save sharedPreferences
    fun saveReference() {
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        if (mymoodtodayList.size == 0) {
            editor.clear()
            editor.apply()
        } else {
            val moodListJson = gson.toJson(mymoodtodayList)
            // Put the  Json representation, which is a string, into sharedPreferences
            editor.putString("moods", moodListJson)
            // Apply the changes
            editor.apply()
        }
    }


    //load sharedPreferences
    fun loadReference() {
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Retrieve data using the key, default value is empty string in case no saved data in there
        val moodstoday = sharedPreferences.getString("moods", "") ?: ""
        if (moodstoday.isNotEmpty()) {
            // Create an instance of Gson
            val gson = Gson()
            // create an object expression that descends from TypeToken
            // and then get the Java Type from that
            val sType = object : TypeToken<List<String>>() {}.type
            // provide the type specified above to fromJson() method
            // this will deserialize the previously saved Json into an object of the specified type (e.g., list)
            val savedmoodList = gson.fromJson<List<String>>(moodstoday, sType)
            // Clear the list and checkboxes in case there are some items
            mymoodtodayList.clear()
//            myAdapter.notifyDataSetChanged()
            // Iterate each item in the list and select the corresponding checkbox
            for (task in savedmoodList) {
                mymoodtodayList.add(task)
                mymoodtodayAdapter.notifyDataSetChanged()
                moodProgressBar()
            }
        } else {
            moodlist1.emptyView
        }
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
        redirectActivity(QuotesActivity());
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

    //progress bar: Reference: https://www.youtube.com/watch?v=vFziVSSIX6Q
    fun moodProgressBar() {
        val mood_progress_bar = findViewById<ProgressBar>(R.id.mood_progressBar)
        var negtive_emotion_count = 0
        if (mymoodtodayList.isNotEmpty()) {

            for (item in mymoodtodayList) {
                Log.d(TAG1, item)
                when (item.split("——").toTypedArray()[1]) {
                    MOOD_LIST[2], MOOD_LIST[3], MOOD_LIST[6], MOOD_LIST[7], MOOD_LIST[8], MOOD_LIST[9] -> {
                        negtive_emotion_count++;
                        Log.d(TAG1, "${negtive_emotion_count}")
                        Log.d(TAG1, item.split("——").toTypedArray()[1])
                    }
                }
            }
            val current_progress = negtive_emotion_count * 100 / mymoodtodayList.size
            Log.d(TAG1, current_progress.toString())
            mood_progress_bar.setProgress(current_progress)
            if (current_progress == 100) {
                Toast.makeText(this, "It'll get better", Toast.LENGTH_SHORT).show()
            }
            if (current_progress == 0) {
                Toast.makeText(this, "Keep it up", Toast.LENGTH_SHORT).show()
            }
        } else {
            mood_progress_bar.setProgress(0)
            mood_progress_bar.max = 100
        }

    }

    //notification
    private fun shownotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
                .apply {
                }
            val channel_manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channel_manager.createNotificationChannel(channel)
        }
    }


    //play music in the background until the activity is destroyed
    fun mediaplay() {
        //check if the music is released before created, release the music when the activity is destroyed
        Log.d(TAG1, "mediaplay ${music_isreleased}")
        if (music_isreleased == false) {
            Log.d(TAG1, "release")
            media_player.release()
            music_isreleased = true
        }
        media_player = MediaPlayer.create(this, R.raw.bg_music)
        Log.d(TAG1, "create ${music_isreleased}")
        media_player.isLooping
        media_player.start()
        music_isreleased = false
        Log.d(TAG1, "musicstart ${music_isreleased}")
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG1, "onstart")
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG1, "onresume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG1, "onrestart")
    }

    override fun onPause() {
        saveReference()
        super.onPause()
        Log.d(TAG1, "onpause")
        closeDrawer(maindrawerLayout)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG1, "onstop")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG1, "ondestroy")
        media_player.stop()
        media_player.release()
        music_isreleased = true
    }

}
