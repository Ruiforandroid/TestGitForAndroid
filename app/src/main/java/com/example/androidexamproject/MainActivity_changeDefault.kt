package com.example.androidexamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_change_default.*
import kotlinx.android.synthetic.main.fragment_cloud.*

class MainActivity_changeDefault : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_change_default)

        val data = mutableListOf<String>()
        val info = mutableListOf<diqu>()
        val openSqLiteHelper = this?.let { MyOpenSqLiteHelper(it, 10) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        cursor = db.query(TABLE_NAME_AREA, null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            textView_nowDefault.text = cursor.getString(cursor.getColumnIndex("cityname"))
        }
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
        listView_changeDefault.adapter=adapter

        listView_changeDefault.setOnItemClickListener{_,_,position,_ ->
            db.execSQL("update $TABLE_NAME_AREA set cityname= ?  where _id= ? ", arrayOf(info.get(position).city_name , 1))
            db.execSQL("update $TABLE_NAME_AREA set province= ?  where _id= ? ", arrayOf(info.get(position).province , 1))
            db.execSQL("update $TABLE_NAME_AREA set citycode= ?  where _id= ? ", arrayOf(info.get(position).city_code , 1))
            Log.d("CHANGEDEFAULT","success")
            val vibrator = this.getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(100)
            finish()
        }



    }
}