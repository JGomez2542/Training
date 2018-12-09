package com.example.jasongomez.listsandrecyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CelebrityListAdapter(var applicationContext: Context, resource: Int,
                           var celebrityList: List<Celebrity>) :
        ArrayAdapter<Celebrity>(applicationContext, resource, celebrityList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val celebrityTemp = celebrityList[position]
        val result = setUpViewHolder()
        val viewHolder: ViewHolder = convertView?.let { it.tag as ViewHolder } ?: result.first
        viewHolder.apply {
            name.text = celebrityTemp.name
            age.text = celebrityTemp.age.toString()
            weight.text = celebrityTemp.weight.toString()
        }
        return convertView ?: result.second
    }

    private fun setUpConvertView() = LayoutInflater.from(applicationContext).inflate(R.layout.celebrity_list_item, null)

    private fun setUpViewHolder(): Pair<ViewHolder, View> {
        val viewHolder = ViewHolder()
        val tempConvertView = setUpConvertView()
        viewHolder.apply {
            name = tempConvertView.findViewById(R.id.tvCelebName)
            age = tempConvertView.findViewById(R.id.tvCelebAge)
            weight = tempConvertView.findViewById(R.id.tvCelebWeight)
        }
        tempConvertView.tag = viewHolder
        return Pair(viewHolder, tempConvertView)
    }

    class ViewHolder {
        lateinit var name: TextView
        lateinit var age: TextView
        lateinit var weight: TextView
    }
}