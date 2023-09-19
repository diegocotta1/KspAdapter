package com.cotta.diego.kspadapter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cotta.diego.kspadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.adapter = ItemAdapter {
            Toast.makeText(this, "${it.title} ${it.subtitle}", Toast.LENGTH_LONG).show()
        }
    }
}