package com.example.androidexamproject

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
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
import kotlinx.android.synthetic.main.fragment_cloud.*
import java.io.BufferedReader
import java.io.InputStreamReader

const val cloudURL = "http://t.weather.itboy.net/api/weather/city/"


class CloudFragment : Fragment() {

    lateinit var adapter: MyRecyclerViewAdapter
    lateinit var db: SQLiteDatabase
    lateinit var cursor: Cursor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cloud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSqLiteHelper = this.context?.let { MyOpenSqLiteHelper(it, 8)}
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
            textView_tianqi.text = weather.data.forecast[0].type
            textView_wendu.text = weather.data.wendu+"℃"
            Log.d("Cloud","成功")
            val adapter2 = this.context?.let { it1 -> ArrayAdapter<Forecast>(it1,android.R.layout.simple_list_item_1,weather.data.forecast) }
            listView.adapter = adapter2
        },{
            Log.d("Cloud","失败")
        })
        queue.add(stringRequest)

    }

    fun reloadAllData() {
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        adapter.swapCursor(cursor)
    }

    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}

