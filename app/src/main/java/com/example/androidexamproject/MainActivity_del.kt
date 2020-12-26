package com.example.androidexamproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main_add_area.*
import kotlinx.android.synthetic.main.activity_main_del.*
import kotlinx.android.synthetic.main.activity_main_del.view.*
import kotlinx.android.synthetic.main.fragment_cloud.*

class MainActivity_del : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_del)

        var lastPosition = 1000
        var nowPosition = 1000

        val data = mutableListOf<String>()
        val info = mutableListOf<diqu>()
        val openSqLiteHelper = this?.let { MyOpenSqLiteHelper(it, 10) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        cursor = db.query(TABLE_NAME_AREA, null, null, null, null, null, null)
        cursor.moveToNext()
        while (cursor.moveToNext()){
            val cityname = cursor.getString(cursor.getColumnIndex("cityname"))
            val province = cursor.getString(cursor.getColumnIndex("province"))
            val citycode = cursor.getString(cursor.getColumnIndex("citycode"))
            val diqu = diqu(cityname,citycode,province)
            data.add(diqu.toString())
            info.add(diqu)
        }
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listView_del.adapter=adapter

        listView_del.setOnItemClickListener{_,view,position,_ ->
//            val diqu = data.get(position)
            if (lastPosition!=1000){
                listView_del[lastPosition].setBackgroundColor(Color.parseColor("#ffffff"))
                listView_del[position].setBackgroundColor(Color.parseColor("#f34649"))
            }else{
                listView_del[position].setBackgroundColor(Color.parseColor("#f34649"))
            }
            lastPosition = position
        }

        button_suredel.setOnClickListener {
            db.execSQL("delete from $TABLE_NAME_AREA where citycode=${info.get(lastPosition).city_code} and _id!=1")
            finish()
        }

        button_quxiaodel.setOnClickListener {
            listView_del[lastPosition].setBackgroundColor(Color.parseColor("#ffffff"))
            lastPosition=1000
        }



    }
}