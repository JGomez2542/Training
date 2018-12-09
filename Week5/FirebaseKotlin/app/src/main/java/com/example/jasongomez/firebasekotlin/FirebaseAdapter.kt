package com.example.jasongomez.firebasekotlin

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.jasongomez.firebasekotlin.databinding.ItemMessageBinding

class FirebaseAdapter(var chatMessages: ArrayList<Message>) : RecyclerView.Adapter<FirebaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMessageBinding>(LayoutInflater.from(parent.context), R.layout.item_message,
                parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = chatMessages.size

    override fun onBindViewHolder(holder: FirebaseAdapter.ViewHolder, position: Int) {
        var temp = chatMessages[position]
        holder.binding.messageTextView.text = temp.text
        holder.binding.nameTextView.text = temp.name
    }

    class ViewHolder(var binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)
}