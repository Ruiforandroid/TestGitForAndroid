package com.example.androidexamproject

class diqu (val city_name: String,val city_code: String, val province: String){
    override fun toString(): String {
        return city_name + "   " + province
    }
}