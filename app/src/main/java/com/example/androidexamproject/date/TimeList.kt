package com.example.androidexamproject.date

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androidexamproject.*
import com.example.androidexamproject.weather.Forecast
import com.example.androidexamproject.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_cloud.*

class TimeList() {

    var daterandom = mutableListOf<DateList>()
    val mounthbig = arrayOf(1,3,5,7,8,10,12)
    val mouthsmall = arrayOf(4,6,9,11)

    init {
        Log.d("TimeList","被调用")
        val datelist = DateList(19,"备注",true)
        val mouth = thismouth
        val year = thisyear
        val thisday = today

        var isyear = if (year%4==0 && year%100!=0 || year%400==0){
            29
        }else{
            28
        }
        var moutype = when(mouth){
            in mounthbig -> 31
            in mouthsmall -> 30
            else -> isyear
        }
        var i = 0   //用于计算本日之后的日子
        val week = thisweek //从数据库里拿
            when(week){
                "星期一" -> {
                    var num = thisday
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                        }
                    daterandom[0].ischoose=true
                    }
                "星期二" -> {
                    var num = thisday
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[1].ischoose=true
                }
                "星期三" -> {
                    var num = thisday
                    daterandom.add(DateList(num-2,""))
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[2].ischoose=true
                }
                "星期四" -> {
                    var num = thisday
                    daterandom.add(DateList(num-3,""))
                    daterandom.add(DateList(num-2,""))
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[3].ischoose=true
                }
                "星期五" -> {
                    var num = thisday
                    daterandom.add(DateList(num-4,""))
                    daterandom.add(DateList(num-3,""))
                    daterandom.add(DateList(num-2,""))
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[4].ischoose=true
                }
                "星期六" -> {
                    var num = thisday
                    daterandom.add(DateList(num-5,""))
                    daterandom.add(DateList(num-4,""))
                    daterandom.add(DateList(num-3,""))
                    daterandom.add(DateList(num-2,""))
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[5].ischoose=true
                }
                else -> {
                    var num = thisday
                    daterandom.add(DateList(num-6,""))
                    daterandom.add(DateList(num-5,""))
                    daterandom.add(DateList(num-4,""))
                    daterandom.add(DateList(num-3,""))
                    daterandom.add(DateList(num-2,""))
                    daterandom.add(DateList(num-1,""))
                    while (i < moutype){
                        var riqi = if ((num+i)%moutype==0){
                            moutype
                        }else{
                            (num+i)%moutype
                        }
                        daterandom.add(DateList(riqi,"您今天有课"))
                        i++
                    }
                    daterandom[6].ischoose=true
                }
            }
    }



}