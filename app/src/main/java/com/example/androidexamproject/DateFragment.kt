package com.example.androidexamproject

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexamproject.date.DateG
import kotlinx.android.synthetic.main.fragment_date.*


class DateFragment : Fragment() {

    companion object {
        private lateinit var game: DateG
    }

    lateinit var adapter:DateCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSqLiteHelper = this.context?.let { MyOpenSqLiteHelper(it, 10)}
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }

        var cursor = db.query(TABLE_NAME_BEIWANG, null, null, null, null, null, null)


        var chooseDay = 0
        textView_year.text = thisyear.toString()


        val recycle = view.findViewById<RecyclerView>(R.id.recyckerView_date)
        game=DateG()
        for (i in 0..30){
            val beiwangdate = (thisyear.toString().substring(2,4)+ thismouth+game.dates[i].date).toInt()
            cursor = db.rawQuery("Select * from $TABLE_NAME_BEIWANG where date=$beiwangdate",null)
            if (cursor.moveToFirst()){
                game.dates[i].beiwang = cursor.getString(cursor.getColumnIndex("beiwang"))
            }
        }
        adapter= DateCardAdapter(game)
        recycle.adapter = adapter
        var number = 0
        recycle.layoutManager = GridLayoutManager(this.context,7)
        adapter.notifyDataSetChanged()
        adapter.setOnCardClickListener {
            game.chooseCard(it)
            adapter.notifyDataSetChanged()

            chooseDay = game.dates[it].date
            val chooseday = (thisyear.toString().substring(2,4)+ thismouth+chooseDay).toInt()
            cursor = db.rawQuery("Select * from $TABLE_NAME_BEIWANG where date=$chooseday",null)
            if (cursor.moveToFirst()){
                val beiwangshuju = cursor.getString(cursor.getColumnIndex("beiwang"))
                editText_beiwang.hint = beiwangshuju
            }else{
                editText_beiwang.hint = ""
                editText_beiwang.text.clear()
            }
            number = it
        }
        button_add_beiwang.setOnClickListener {
            val chooseday = (thisyear.toString().substring(2,4)+ thismouth+chooseDay).toInt()
            val choosedaybeiwang = editText_beiwang.text.toString()
            val contentValues = ContentValues().apply{
                put("date", chooseday)
                put("beiwang",choosedaybeiwang)
            }
            db.insert(TABLE_NAME_BEIWANG, null, contentValues)
            Log.d("databaseBeiwang","成功")
            game.dates[number].beiwang = choosedaybeiwang
        }
        button_del.setOnClickListener {
            val chooseday = (thisyear.toString().substring(2,4)+ thismouth+chooseDay).toInt()
            db.execSQL("delete FROM $TABLE_NAME_BEIWANG where date=$chooseday")
            editText_beiwang.hint=""
        }

    }

}