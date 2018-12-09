package com.example.jasongomez.listsandrecyclerviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity(), ClickListener {

    override fun onClick() {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG).show()
    }

    private val celebrityList: MutableList<Celebrity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        celebrityList.add(Celebrity("Drake", 32, 200))
        celebrityList.add(Celebrity("Jennifer Aniston", 50, 130))
        celebrityList.add(Celebrity("Tom Cruise", 32, 170))

        rvMain.apply {
            layoutManager = LinearLayoutManager(this@RecyclerActivity)
            adapter = CelebrityRecyclerAdapter(celebrityList, this@RecyclerActivity)
            itemAnimator = DefaultItemAnimator()
        }
    }
}
