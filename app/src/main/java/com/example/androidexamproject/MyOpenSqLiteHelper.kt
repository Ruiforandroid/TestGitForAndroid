package com.example.androidexamproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

const val DB_NAME = "examdb5.db"
const val TABLE_NAME = "exam5cityall"
const val TABLE_NAME_AREA = "examarea5all"
const val TABLE_NAME_CITY = "examallcityall"
const val TABLE_NAME_BEIWANG = "beiwangall"


class MyOpenSqLiteHelper(context: Context, version:Int):SQLiteOpenHelper(context, DB_NAME,null,version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME(_id integer primary key autoincrement, citycode text, cityname text)")
        db?.execSQL("create table $TABLE_NAME_AREA(_id integer primary key autoincrement,cityname text, citycode text, province text)")
        db?.execSQL("create table $TABLE_NAME_CITY(_id integer primary key autoincrement, citycode text, cityname text)")
        db?.execSQL("create table $TABLE_NAME_BEIWANG(_id integer primary key autoincrement, date Int, beiwang text)")
        Log.d("database","创建成功")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists $TABLE_NAME")
        db?.execSQL("drop table if exists $TABLE_NAME_AREA")
        db?.execSQL("drop table if exists $TABLE_NAME_CITY")
        db?.execSQL("drop table if exists $TABLE_NAME_BEIWANG")
        onCreate(db)
        Log.d("database","更新成功")
    }

}