package com.example.myapp01.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp01.Database.DiaryEntity
import com.example.myapp01.R
import kotlinx.android.synthetic.main.diary_item.view.*
import okhttp3.OkHttpClient
import okhttp3.Request

class DiaryAdapter(val listener: RowClickListener) :
    RecyclerView.Adapter<DiaryAdapter.MyViewHolder>() {

    //list
    var items = ArrayList<DiaryEntity>()

    fun setListData(data: ArrayList<DiaryEntity>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate a layout from our XML (diary_item.XML) and return the holder
        // create a new view
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }
        holder.bind(items[position])

    }

    class MyViewHolder(view: View, private val listener: RowClickListener) :
        RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.title_tv
        private val tvConent: TextView = view.content_tv
        private val deleteUserID: ImageView = view.delete_btn
        private val analyzeEmotionID: ImageView = view.analyze_btn

        // Class will represent a single row in our recyclerView list
        // Class also allows caching views and reusing theme


        fun bind(data: DiaryEntity) {
            tvTitle.text = data.title

            tvConent.text = data.content

            deleteUserID.setOnClickListener {
                listener.onDeleteClickListener(data)
            }

            analyzeEmotionID.setOnClickListener {
                listener.onAnalyseClickListener(data)
            }
        }

//        private fun doAnalyzeEmotion(Text:String) {
//            val client = OkHttpClient()
//            val request = Request.Builder()
//                .url("https://twinword-emotion-analysis-v1.p.rapidapi.com/analyze/?text=${Text}")
//                .get()
//                .addHeader("X-RapidAPI-Host", "twinword-emotion-analysis-v1.p.rapidapi.com")
//                .addHeader("X-RapidAPI-Key", "994bdfbf94msh3b119a9d7f448adp13da27jsnb83b6a87ab9a")
//                .build()
//
//            val response = client.newCall(request).execute()
//            Log.d("Main", response.toString())
//
//        }
    }

    //row click listener, used for on delete function, the onItemClickListener and for onAnalyze
    interface RowClickListener {
        fun onDeleteClickListener(diary: DiaryEntity)
        fun onItemClickListener(diary: DiaryEntity)
        fun onAnalyseClickListener(diary: DiaryEntity)
    }

    override fun getItemCount(): Int {
        // Return the size of your dataset
        return items.size
    }
}