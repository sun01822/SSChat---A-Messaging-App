package com.sun.sschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.sun.sschat.R
import com.sun.sschat.databinding.ReceiverItemLayoutBinding
import com.sun.sschat.databinding.SentItemLayoutBinding
import com.sun.sschat.model.MessageModel

class MessageAdapter(var context: Context, var list: ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_SENT = 1
    var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_SENT)
                SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_item_layout, parent, false))
                else
                ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receiver_item_layout, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT
               else ITEM_RECEIVE
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var message = list[position]

        if(holder.itemViewType == ITEM_SENT){
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.userMsg.text = message.message
        }
        else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.userMsg.text = message.message
        }
    }

    inner class SentViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = SentItemLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = ReceiverItemLayoutBinding.bind(view)
    }

}