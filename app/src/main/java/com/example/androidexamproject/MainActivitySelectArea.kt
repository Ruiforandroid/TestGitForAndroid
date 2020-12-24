package com.example.androidexamproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_cloud.*


class MainActivitySelectArea : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_select_area)


        val data = mutableListOf<String>()
        val info = mutableListOf<diqu>()
        val openSqLiteHelper = this?.let { MyOpenSqLiteHelper(it, 9) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        cursor = db.query(TABLE_NAME_AREA, null, null, null, null, null, null)
        while (cursor.moveToNext()){
            val cityname = cursor.getString(cursor.getColumnIndex("cityname"))
            val province = cursor.getString(cursor.getColumnIndex("province"))
            val citycode = cursor.getString(cursor.getColumnIndex("citycode"))
            val diqu = diqu(cityname,citycode,province)
            data.add(diqu.toString())
            info.add(diqu)
        }
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listView.adapter=adapter

        listView.setOnItemClickListener{_,_,position,_ ->
//            val diqu = data.get(position)
            Toast.makeText(this,data.get(position),Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.putExtra("CITYCODE",info.get(position).city_code.toString())
            intent.putExtra("PROVINCE",info.get(position).province.toString())
            Log.d("Selectcitycode",info.get(position).city_code.toString())
            setResult(1,intent)
            finish()
        }
    }
}