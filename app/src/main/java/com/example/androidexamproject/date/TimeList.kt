package com.example.androidexamproject.date

import android.util.Log

class TimeList() {

    var daterandom = mutableListOf<DateList>()
    val mounthbig = arrayOf(1,3,5,7,8,10,12)
    val mouthsmall = arrayOf(4,6,9,11)

    init {
        Log.d("TimeList","被调用")
        val datelist = DateList(19,"备注",true)
        val mouth = 12
        val year = 2020
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
        daterandom.add(datelist)
        var i = 0   //用于计算本日之后的日子
        val week = "星期一" //从数据库里拿
            when(week){
                "星期一" -> {
                    var num = datelist.date
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
                "星期二" -> 2
                "星期三" -> 3
                "星期四" -> 4
                "星期五" -> 5
                "星期六" -> 6
                else -> 7
            }
    }
    

}