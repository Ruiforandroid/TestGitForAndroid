package com.example.androidexamproject

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androidexamproject.weather.Forecast
import com.example.androidexamproject.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main_change_default.*
import kotlinx.android.synthetic.main.fragment_cloud.*

const val cloudURL = "http://t.weather.itboy.net/api/weather/city/"
var today = 24
var thismouth = 12
lateinit var thisweek:String
var thisyear = 2020

class CloudFragment : Fragment() {

    lateinit var adapter: MyRecyclerViewAdapter
    lateinit var db: SQLiteDatabase
    lateinit var cursor: Cursor
    lateinit var citycode_diqu:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cloud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSqLiteHelper = this.context?.let { MyOpenSqLiteHelper(it, 10)}
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        var cursor2 = db.query(TABLE_NAME_AREA, null, null, null, null, null, null)
        if (!cursor2.moveToFirst()){
            val cityname = "北京"
            val citycode = "101010100"
            val province = "北京"
            val contentValues = ContentValues().apply {
                put("cityname",cityname)
                put("citycode",citycode)
                put("province",province)
            }
            db.insert(TABLE_NAME_AREA,null,contentValues)
        }
        cursor2.moveToFirst()
        Log.d("Cloud","成功一半")
        val queue = Volley.newRequestQueue(this.context)
        textView_add_province.text = cursor2.getString(cursor2.getColumnIndex("province")).toString()
        textView_didian.text = cursor2.getString(cursor2.getColumnIndex("cityname")).toString()
        val citycode = cursor2.getString(cursor2.getColumnIndex("citycode")).toString()
        val url = (cloudURL+citycode).toString()
        val stringRequest = StringRequest(url,{
            val gson = Gson()
            val WeatherType = object :TypeToken<Weather>(){}.type
            val weather = gson.fromJson<Weather>(it,WeatherType)
            textView_shidu.text = "湿度:" + weather.data.shidu
            today = weather.data.forecast[0].date.toInt()
            thisweek = weather.data.forecast[0].week
            thismouth = weather.date.substring(4,6).toInt()
            thisyear = weather.date.substring(0,4).toInt()
            textView_tianqi.text = weather.data.forecast[0].type
            when(weather.data.forecast[0].type){
                "晴" -> linerLayout.setBackgroundResource(R.drawable.sun)
                "阴" -> linerLayout.setBackgroundResource(R.drawable.yintian)
                "多云" -> linerLayout.setBackgroundResource(R.drawable.cloud)
                "小雪" -> linerLayout.setBackgroundResource(R.drawable.snow)
                "中雪" ->linerLayout.setBackgroundResource(R.drawable.snow)
                "大雪" -> linerLayout.setBackgroundResource(R.drawable.snow)
                "小雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                    changecolor()}
                "中雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                    changecolor()}
                "大雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                    changecolor()}
                else -> linerLayout.setBackgroundResource(R.drawable.wumai)

            }
            textView_wendu.text = weather.data.wendu+"℃"
            Log.d("Cloud","成功")
            val adapter2 = this.context?.let { it1 -> ArrayAdapter<Forecast>(it1,android.R.layout.simple_list_item_1,weather.data.forecast) }
            listView.adapter = adapter2
        },{
            Log.d("Cloud","失败")
        })
        queue.add(stringRequest)
        var days = (thisyear.toString().substring(2,4)+ thismouth+ today.toString()).toInt()
        var cursor4 = db.rawQuery("Select * from $TABLE_NAME_BEIWANG where date=$days",null)
        var lastbeiwang=""
        while (!cursor4.moveToFirst()){
            days=days+1
            cursor4 = db.rawQuery("Select beiwang from $TABLE_NAME_BEIWANG where date=$days",null)
        }
        lastbeiwang=cursor4.getString(cursor4.getColumnIndex("beiwang")).toString()
        textView_beiwang.text = "距离您最近的备忘事件为：   "+lastbeiwang


        textView_didian.setOnClickListener{
            Log.d("didian","可以触发")
            val intent = Intent(activity,MainActivitySelectArea::class.java)
            startActivityForResult(intent,2)

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2){
            if (resultCode==1) {
                //切换城市
                val queue2 = Volley.newRequestQueue(this.context)
                citycode_diqu = data?.getStringExtra("CITYCODE").toString()
                val Province_diqu = data?.getStringExtra("PROVINCE").toString()
                Log.d("GetFromSelect","$citycode_diqu")
                val url = (cloudURL+citycode_diqu).toString()
                val stringRequest2 = StringRequest(url,{
                    val gson = Gson()
                    val WeatherType = object :TypeToken<Weather>(){}.type
                    val weather = gson.fromJson<Weather>(it,WeatherType)
                    textView_didian.text = weather.cityInfo.city.toString()
                    textView_add_province.text = Province_diqu
                    textView_shidu.text = "湿度:" + weather.data.shidu
                    textView_tianqi.text = weather.data.forecast[0].type
                    when(weather.data.forecast[0].type){
                        "晴" -> linerLayout.setBackgroundResource(R.drawable.sun)
                        "阴" -> linerLayout.setBackgroundResource(R.drawable.yintian)
                        "多云" -> linerLayout.setBackgroundResource(R.drawable.cloud)
                        "小雪" -> linerLayout.setBackgroundResource(R.drawable.snow)
                        "中雪" ->linerLayout.setBackgroundResource(R.drawable.snow)
                        "大雪" -> linerLayout.setBackgroundResource(R.drawable.snow)
                        "小雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                            changecolor()}
                        "中雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                            changecolor()}
                        "大雨" -> {linerLayout.setBackgroundResource(R.drawable.rain)
                            changecolor()}
                        else -> linerLayout.setBackgroundResource(R.drawable.wumai)

                    }

                    textView_wendu.text = weather.data.wendu+"℃"
                    Log.d("Cloud","成功")
                    val adapter2 = this.context?.let { it1 -> ArrayAdapter<Forecast>(it1,android.R.layout.simple_list_item_1,weather.data.forecast) }
                    listView.adapter = adapter2
                },{
                    Log.d("Cloud","失败")
                })
                queue2.add(stringRequest2)
            }
        }
    }

    fun changecolor(){
        textView_tianqi.setTextColor(Color.parseColor("#ffffff"))
        textView_beiwang.setTextColor(Color.parseColor("#ffffff"))
        textView_add_province.setTextColor(Color.parseColor("#ffffff"))
        textView_didian.setTextColor(Color.parseColor("#ffffff"))
        textView_shidu.setTextColor(Color.parseColor("#ffffff"))


    }

}

//    fun reloadAllData() {
//        cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
//        adapter.swapCursor(cursor)
//    }
//
//    fun readFileFromRaw(rawName: Int): String? {
//        try {
//            val inputReader = InputStreamReader(resources.openRawResource(rawName))
//            val bufReader = BufferedReader(inputReader)
//            var line: String? = ""
//            var result: String? = ""
//            while (bufReader.readLine().also({ line = it }) != null) {
//                result += line
//            }
//            return result
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return ""
//    }

