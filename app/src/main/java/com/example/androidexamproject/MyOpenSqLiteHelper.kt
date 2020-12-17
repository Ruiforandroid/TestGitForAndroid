package com.example.androidexamproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcelable

const val DB_NAME = "myexamdb3.db"
const val TABLE_NAME = "personexam3"


class MyOpenSqLiteHelper(context: Context, version:Int):SQLiteOpenHelper(context, DB_NAME,null,version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME(_id integer primary key autoincrement, citycode text, cityname text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists $DB_NAME")
        db?.execSQL("drop table if exists $TABLE_NAME")
        TODO("Not yet implemented")
    }

}