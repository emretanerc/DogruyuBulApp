package com.etcmobileapps.dogrunusuogren

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.etcmobileapps.dogrunusuogren.databinding.ActivityMainBinding
import com.etcmobileapps.dogrunusuogren.ui.fragments.MenuFragment
import com.etcmobileapps.dogrunusuogren.ui.fragments.SoloPlayerFragment

private  lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }




}