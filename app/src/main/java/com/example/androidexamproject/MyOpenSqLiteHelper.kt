package com.example.androidexamproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcelable
import android.util.Log
import android.widget.Toast

const val DB_NAME = "examdb5.db"
const val TABLE_NAME = "exam5city"
const val TABLE_NAME_AREA = "examarea5"


class MyOpenSqLiteHelper(context: Context, version:Int):SQLiteOpenHelper(context, DB_NAME,null,version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME(_id integer primary key autoincrement, citycode text, cityname text)")
        db?.execSQL("create table $TABLE_NAME_AREA(_id integer primary key autoincrement,cityname text, citycode text, province text)")
        Log.d("database","创建成功")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists $TABLE_NAME")
        db?.execSQL("drop table if exists $TABLE_NAME_AREA")
        onCreate(db)
        Log.d("database","更新成功")
    }

}