package com.example.jasongomez.listsandrecyclerviews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var celebrityList: MutableList<Celebrity>
    lateinit var stringList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        val arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1,
                stringList)
        lvMain.adapter = arrayAdapter

        val celebrityListAdapter = CelebrityListAdapter(this,
                R.layout.celebrity_list_item, celebrityList)
        lvCelebrity.adapter = celebrityListAdapter
    }

    fun goToRecycler(view: View) {
        val intent = Intent(this, RecyclerActivity::class.java)
        startActivity(intent)
    }

    private fun initData() {
        stringList = ArrayList()
        stringList.add("First")
        stringList.add("Second")
        stringList.add("Third")
        stringList.add("Fourth")

        celebrityList = ArrayList()
        celebrityList.add(Celebrity("Drake", 32, 200))
        celebrityList.add(Celebrity("Jennifer aniston", 50, 130))
        celebrityList.add(Celebrity("Tom Cruise", 54, 170))
    }
}
