package com.example.androidexamproject.date

import android.util.Log
import com.example.androidexamproject.DateFragment
import kotlinx.android.synthetic.main.fragment_cloud.*
import java.io.Serializable

class DateG():Serializable {

    var dates:MutableList<DateList>

    init {
        Log.d("dateG","被调用")
        val deck = TimeList()
        dates = deck.daterandom
    }

    fun chooseCard(index:Int) {
        val card = dates.get(index)
        for (i in 0..29){
            dates[i].ischoose=false
        }
        card.ischoose=true

        Log.d("ischoose",card.ischoose.toString())
    }




}