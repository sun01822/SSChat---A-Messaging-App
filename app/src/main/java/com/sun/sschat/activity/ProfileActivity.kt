package com.sun.sschat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sun.sschat.R
import com.sun.sschat.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}