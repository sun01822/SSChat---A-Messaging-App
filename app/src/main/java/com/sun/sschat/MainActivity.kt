package com.sun.sschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sun.sschat.activity.NumberActivity
import com.sun.sschat.adapter.ViewPagerAdapter
import com.sun.sschat.databinding.ActivityMainBinding
import com.sun.sschat.fragments.CallFragment
import com.sun.sschat.fragments.ChatFragment
import com.sun.sschat.fragments.StatusFragment
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(auth.currentUser == null){
            startActivity(Intent(this, NumberActivity::class.java))
            finish()
        }

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())


        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)
        getServices(auth.currentUser!!.phoneNumber.toString().trim())
    }
    private fun getServices(ID: String){
        val appID : Long = 759924298   // yourAppID
        val appSign = "e81430b0fcdf01ac706557ecf42b628265ad5b94461167ff31a66f8048630b52"  // yourAppSign
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true
        val notificationConfig = ZegoNotificationConfig()
        notificationConfig.sound = "zego_uikit_sound_call"
        notificationConfig.channelID = "CallInvitation"
        notificationConfig.channelName = "CallInvitation"
        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            ID,
            ID,
            callInvitationConfig
        )
        Toast.makeText(this, "Services created successfully", Toast.LENGTH_SHORT).show()
    }
}