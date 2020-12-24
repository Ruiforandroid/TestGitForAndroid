package com.example.androidexamproject

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val fragment1 = CloudFragment()
    val fragment2 = DateFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,fragment1)
                .commit()
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNV)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.game ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment1)
                        .commit()
                R.id.blank ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment2)
                        .commit()
            }
            true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.righttopmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent= Intent(this, MainActivity_add_area::class.java)
        when(item.itemId){
            R.id.add_area -> {Log.d("menu","add被点击")
            startActivity(intent)}
            R.id.del_area -> Log.d("menu","del被点击")
        }
        return super.onOptionsItemSelected(item)
    }




}