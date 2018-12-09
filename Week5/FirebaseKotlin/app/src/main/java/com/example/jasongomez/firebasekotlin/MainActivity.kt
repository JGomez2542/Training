package com.example.jasongomez.firebasekotlin

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jasongomez.firebasekotlin.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAdapter: FirebaseAdapter
    lateinit var firebaseLayoutManager: LinearLayoutManager
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var eventListener: ValueEventListener
    lateinit var authStateListener: FirebaseAuth.AuthStateListener
    var userName: String = Constants.ANONYMOUS
    var messages: ArrayList<Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeRecyclerViewAdapter()
        initializeFirebaseComponents()
        addTextWatchers()
    }

    override fun onStart() {
        super.onStart()
        addAuthStateListener()
    }

    override fun onResume() {
        super.onResume()
        addReadListeners()
    }

    override fun onPause() {
        super.onPause()
        removeReadListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeAuthStateListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RC_SIGN_IN
                && resultCode == Activity.RESULT_OK)
            Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
        else finish()
    }

    fun sendMessage(view: View) {
        var message = Message(binding.messageEditText.text.toString(), userName)
        databaseReference.push().setValue(message)
        //Clear input box
        binding.messageEditText.setText("")
    }

    fun signOut(view: View) {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    onSignedOutCleanup()
                }
    }

    private fun addReadListeners() {
        Log.d("MainActivity", "Adding Listener")
        eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshots: DataSnapshot) {
                messages.clear()
                dataSnapshots.children.forEach { dataSnapshot ->
                    var message = dataSnapshot.getValue(Message::class.java)
                    message?.let {
                        messages.add(it)
                    }
                }
                firebaseAdapter.notifyDataSetChanged()
            }
        }
        databaseReference.addValueEventListener(eventListener)
    }

    private fun removeReadListeners() {
        databaseReference.removeEventListener(eventListener)
    }

    private fun addAuthStateListener() {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            firebaseAuth.currentUser?.let {
                it.displayName?.let { it1 -> onSignedInInitialize(it1) }
            } ?: onSignedOutCleanup()
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private fun removeAuthStateListener() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    private fun addTextWatchers() {
        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.sendButton.isEnabled = charSequence.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }


    private fun onSignedInInitialize(userName: String) {
        this.userName = userName
    }

    private fun onSignedOutCleanup() {
        userName = Constants.ANONYMOUS
        firebaseAdapter.clear()
        startFirebaseSignIn()
    }

    private fun initializeFirebaseComponents() {
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("chatMessages")
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun initializeRecyclerViewAdapter() {
        firebaseLayoutManager = LinearLayoutManager(this)
        firebaseAdapter = FirebaseAdapter(messages)
        binding.recyclerView.apply {
            adapter = firebaseAdapter
            layoutManager = firebaseLayoutManager
        }
    }


    private fun FirebaseAdapter.clear() {
        chatMessages.clear()
        notifyDataSetChanged()
    }


    private fun startFirebaseSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                listOf(AuthUI.IdpConfig.GoogleBuilder().build(),
                                        AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),
                Constants.RC_SIGN_IN)

    }

    private fun Any?.isNotNull(): Boolean {
        return this != null
    }
}
