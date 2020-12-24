package com.example.androidexamproject

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main_add_area.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception


lateinit var adapter: MyRecyclerViewAdapter
lateinit var db: SQLiteDatabase
lateinit var cursor: Cursor
var add_pro = ""
var add_shi = ""
var add_city = ""
var add_code = ""
var count = 0
var issuccess = true
lateinit var add_city_code :String

class MainActivity_add_area : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_add_area)

        val openSqLiteHelper = this?.let { MyOpenSqLiteHelper(it, 10) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        adapter = MyRecyclerViewAdapter(cursor)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        if (!cursor.moveToFirst()) {
            val str = readFileFromRaw(R.raw.citycodepri)
            val gson = Gson()
            val CityType = object : TypeToken<List<City>>() {}.type   //List<City>还是City
            var cities: List<City> = gson.fromJson(str, CityType)
            var i = 0
            while (cities[i].city_name != "") {
                val cityname = cities[i].city_name
                val citycode = cities[i].city_code
                val contentValues = ContentValues().apply {
                    put("citycode", citycode)
                    put("cityname", cityname)
                }
                i++
                db.insert(TABLE_NAME, null, contentValues)
                cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
                reloadAllData(cursor)
            }
        }

        cursor = db.query(TABLE_NAME_CITY, null, null, null, null, null, null)
        if (add_pro != "") {
            if (add_code.length > 6) {
                textView_add_province.text = add_pro
                textView_add_shi.text = add_pro
                textView_add_city.text = add_pro
            } else {
                if (!cursor.moveToFirst()) {
                    val str = readFileFromRaw(R.raw.citycode)
                    val gson = Gson()
                    val CityType = object : TypeToken<List<City>>() {}.type   //List<City>还是City
                    var cities: List<City> = gson.fromJson(str, CityType)
                    var i = 0
                    while (cities[i].city_name != "") {
                        val cityname = cities[i].city_name
                        val citycode = cities[i].city_code
                        val contentValues = ContentValues().apply {
                            put("citycode", citycode)
                            put("cityname", cityname)
                        }
                        i++
                        db.insert(TABLE_NAME_CITY, null, contentValues)
                    }
                }
                cursor = db.query(TABLE_NAME_CITY, null, "citycode like '$add_code%'", null, null, null, null)
                reloadAllData(cursor)
                textView_add_province.text = add_pro
            }
        }
        if (add_shi != "") {
            cursor = db.query(TABLE_NAME_CITY, null, "citycode like '$add_shi%'", null, null, null, null
            )
            reloadAllData(cursor)
            textView_add_shi.text = add_shi
        }
        if (add_city != "") {
            textView_add_city.text = add_city
        }




        button_add_beiwang.setOnClickListener{
            textView_add_province.text = add_pro
            textView_add_shi.text = add_shi
            textView_add_city.text = add_city

            cursor = db.query(TABLE_NAME_CITY, null, null, null, null, null, null)
            if (add_pro != "") {
                if (add_code.length > 6 && issuccess) {
                    textView_add_province.text = add_pro
                    textView_add_shi.text = add_pro
                    textView_add_city.text = add_pro
                } else {
                    if (!cursor.moveToFirst()) {
                        val str = readFileFromRaw(R.raw.citycode)
                        val gson = Gson()
                        val CityType = object : TypeToken<List<City>>() {}.type   //List<City>还是City
                        var cities: List<City> = gson.fromJson(str, CityType)
                        var i = 0
                        while (cities[i].city_name != "") {
                            val cityname = cities[i].city_name
                            val citycode = cities[i].city_code
                            val contentValues = ContentValues().apply {
                                put("citycode", citycode)
                                put("cityname", cityname)
                            }
                            i++
                            db.insert(TABLE_NAME_CITY, null, contentValues)
                        }
                    }
                    cursor = db.query(TABLE_NAME_CITY, null, "citycode like '$add_code%01'", null, null, null, null)
                    reloadAllData(cursor)
                    textView_add_province.text = add_pro
                    issuccess = false
                }
            }
            Log.d("cityinfo","$add_shi")
            if (add_shi != "") {
                add_code = add_code.substring(0,7)
                Log.d("cityinfo","$add_code")
                cursor = db.query(TABLE_NAME_CITY, null, "citycode like '$add_code%'", null, null, null, null)
                reloadAllData(cursor)
                textView_add_shi.text = add_shi

            }
            if (add_city != "") {
                textView_add_city.text = add_city

            }
        }

        button_sure.setOnClickListener {
            Log.d("add_info_citycode", add_code)
            if (add_city!=""){
                val values = ContentValues().apply {
                    put("cityname", add_city)
                    put("citycode", add_city_code)
                    put("province", add_pro)
                }
                db.insert(TABLE_NAME_AREA,null,values)
            }else{
                Toast.makeText(this, "还有部分信息没选择哟！！！", Toast.LENGTH_LONG).show()
            }

        }

        button_quxiao.setOnClickListener{
            add_pro = ""
            add_shi = ""
            add_city = ""
            add_code = ""
            count = 0
        }

    }

    fun reloadAllData(cursor: Cursor) {
        adapter.swapCursor(cursor)
    }

    fun readFileFromRaw(rawName:Int):String? {
        try{
            val inputReader = InputStreamReader(resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line:String?=""
            var result:String?=""
            while (bufReader.readLine().also({line=it})!=null){
                result += line
            }
            return result
        }catch (e: Exception){
            e.printStackTrace()
        }
        return ""
    }

    public fun changeview(text:String){
        textView_add_province.text = text
    }

}
class MyRecyclerViewAdapter(var cursor: Cursor): RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    fun swapCursor(newCursor: Cursor) {
        if (cursor == newCursor) return
        cursor.close()
        cursor = newCursor
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_cityname: TextView
        val textView_citycode: TextView

        init {
            textView_cityname = view.findViewById(R.id.textView_cityame)
            textView_citycode = view.findViewById(R.id.textView_citycode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.person_layout, parent, false)
        //以下实现点击事件
        val viewHolder = ViewHolder(view)
        viewHolder.textView_cityname.setOnClickListener {
            val position = viewHolder.textView_citycode.text.toString()
            Toast.makeText(parent.context, "you click this $position", Toast.LENGTH_LONG).show()

            if (count == 0){
                add_pro = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
                Log.d("citychoose","成功选择省份")
            }else if (count==1){
                add_shi = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
                Log.d("citychoose","成功选择市")
            }else{
                add_city = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
                add_city_code = add_code
                Log.d("add_info_citycode", add_code)

            }
            count++

        }
        viewHolder.textView_citycode.setOnClickListener {
            val position = viewHolder.textView_citycode.text.toString()
            Log.d("position", "$position")
            Toast.makeText(parent.context, "you click this $position", Toast.LENGTH_SHORT).show()
            if (count == 0){
                add_pro = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
            }else if (count==1){
                add_shi = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
            }else{
                add_city = viewHolder.textView_cityname.text.toString()
                add_code = viewHolder.textView_citycode.text.toString()
            }
            count++
        }
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.textView_cityname.text = cursor.getString(cursor.getColumnIndex("cityname"))
        holder.textView_citycode.text = cursor.getString(cursor.getColumnIndex("citycode"))
    }

    override fun getItemCount(): Int {
        return cursor.count
    }


}