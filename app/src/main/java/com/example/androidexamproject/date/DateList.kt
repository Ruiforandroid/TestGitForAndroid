package com.example.androidexamproject.date

import java.io.Serializable

class DateList (var date:Int,var beiwang:String,var istoday:Boolean = false,var ischoose:Boolean=false):Serializable{

    override fun toString(): String {
        return date.toString()+"  "+beiwang
    }

}