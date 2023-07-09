package com.sun.sschat.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sun.sschat.adapter.MessageAdapter
import com.sun.sschat.databinding.ActivityChatBinding
import com.sun.sschat.model.MessageModel
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private lateinit var senderUid: String
    private lateinit var receiverUid: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String

    private lateinit var list : ArrayList<MessageModel>
    private lateinit var username : String
    private lateinit var usernumber : String
    val appID: Long = 759924298
    val appSign: String = "e81430b0fcdf01ac706557ecf42b628265ad5b94461167ff31a66f8048630b52"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        senderUid = auth.uid.toString()
        receiverUid = intent.getStringExtra("uid")!!
        username = intent.getStringExtra("userName")!!
        usernumber = intent.getStringExtra("phone")!!
        binding.userName.text = username
        Glide.with(binding.userImage).load(intent.getStringExtra("userImage")).into(binding.userImage)

        list = ArrayList()

        senderRoom = senderUid+receiverUid
        receiverRoom = receiverUid+senderUid

        binding.imageView2.setOnClickListener {
            if(binding.messageBox.text.isEmpty()){
                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
            }else{
                val message = MessageModel(binding.messageBox.text.toString(), senderUid, Date().time)

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom)
                    .child("message")
                    .child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        database.reference.child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(randomKey)
                            .setValue(message)
                            .addOnSuccessListener {
                                binding.messageBox.text = null
                            }
                    }

            }
        }
        database.reference.child("chats")
            .child(senderRoom)
            .child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }
                    binding.recyclerView.adapter = MessageAdapter(this@ChatActivity, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            })

        binding.call.setOnClickListener {
            val intent  = Intent(this@ChatActivity, CallActivity::class.java)
            intent.putExtra("receiverId", usernumber)
            startActivity(intent)
        }
    }
}