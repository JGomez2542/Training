package com.example.jasongomez.listsandrecyclerviews

import android.nfc.Tag
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CelebrityRecyclerAdapter(var celebrityList: List<Celebrity>, var clickListener: ClickListener) : RecyclerView.Adapter<CelebrityRecyclerAdapter.ViewHolder>() {

    val TAG = "Adapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebrityRecyclerAdapter.ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.celebrity_recycler_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount() = celebrityList.size

    override fun onBindViewHolder(holder: CelebrityRecyclerAdapter.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val celebrityTemp = celebrityList[position]
        holder.apply {
            tvCelebrityName.text = celebrityTemp.name
            tvCelebrityAge.text = celebrityTemp.age.toString()
            tvCelebrityWeight.text = celebrityTemp.weight.toString()
        }
        holder.tvCardView.setOnClickListener {
            clickListener.onClick()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCelebrityName: TextView = itemView.findViewById(R.id.tvCelebNameRecycler)
        val tvCelebrityAge: TextView = itemView.findViewById(R.id.tvCelebAgeRecycler)
        val tvCelebrityWeight: TextView = itemView.findViewById(R.id.tvCelebWeightRecycler)
        val tvCardView: CardView = itemView.findViewById(R.id.cardView)
    }
}