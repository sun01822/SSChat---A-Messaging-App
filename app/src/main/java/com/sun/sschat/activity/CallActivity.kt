package com.sun.sschat.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sun.sschat.databinding.ActivityCallBinding
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

class CallActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCallBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var receiverID: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        receiverID = intent.getStringExtra("receiverId").toString().trim()

        binding.userName.text = "Hey" + auth.currentUser!!.phoneNumber.toString()
        binding.receiverId.setText(receiverID)

        if(receiverID.isNotEmpty()){
            setVoiceCall(receiverID.trim())
            setVideoCall(receiverID.trim())
        }
    }

    private fun setVoiceCall(targetUserID: String){
        binding.voiceCallBtn.setIsVideoCall(false)
        binding.voiceCallBtn.resourceID = "zego_uikit_call"
        binding.voiceCallBtn.setInvitees(
            listOf(
                ZegoUIKitUser(
                    targetUserID,
                    targetUserID
                )
            ))
    }

    private fun setVideoCall(targetUserID: String){
        binding.videoCallBtn.setIsVideoCall(true)
        binding.videoCallBtn.resourceID = "zego_uikit_call"
        binding.videoCallBtn.setInvitees(
            listOf(
                ZegoUIKitUser(
                    targetUserID,
                    targetUserID
                )
            ))
    }

}