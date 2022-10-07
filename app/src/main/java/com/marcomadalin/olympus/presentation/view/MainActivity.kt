package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.marcomadalin.olympus.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.navbar.setupWithNavController(findNavController(binding.fragView))

        binding.navbar.menu.forEach { item ->
            item.actionView?.setOnLongClickListener { view ->
                view.performClick()
                TooltipCompat.setTooltipText(view, null)
                true
            }
        }
    }
}