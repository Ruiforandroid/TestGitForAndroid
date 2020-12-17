package com.example.androidexamproject

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_cloud.*
import java.io.BufferedReader
import java.io.InputStreamReader

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CloudFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

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

        val openSqLiteHelper = this.context?.let { MyOpenSqLiteHelper(it,2 ) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        adapter = MyRecyclerViewAdapter(cursor)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
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
                reloadAllData()
            }
        }

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
            Toast.makeText(parent.context, "you click this $position", Toast.LENGTH_SHORT).show()
        }
        viewHolder.textView_citycode.setOnClickListener {
            val position = viewHolder.textView_citycode.text.toString()
            Log.d("position", "$position")
            Toast.makeText(parent.context, "you click this $position", Toast.LENGTH_SHORT).show()
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