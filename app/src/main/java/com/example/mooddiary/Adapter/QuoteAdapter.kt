package com.example.myapp01.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp01.Quote
import com.example.myapp01.R

class QuoteAdapter(private val quotes: ArrayList<Quote>) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quote_content = itemView.findViewById<TextView>(R.id.quotes_textview)
        val quote_author = itemView.findViewById<TextView>(R.id.author_textview)
    }

    //inflate view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quote_card_layout, parent, false)
        return QuoteViewHolder(view)
    }

    //bind the view with data
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentItem = quotes[position]
        holder.quote_content.text = currentItem.content
        holder.quote_author.text = currentItem.author
    }

    //get the size of quotes
    override fun getItemCount(): Int {
        return quotes.size
    }
}