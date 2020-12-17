package com.example.androidexamproject

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexamproject.date.DateG
import com.example.androidexamproject.date.DateList
import com.example.androidexamproject.date.TimeList

class DateCardAdapter(val Game:DateG):RecyclerView.Adapter<DateCardAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val cardButton:Button
        init {
            cardButton = itemView.findViewById(R.id.cardButton)
        }
    }

    private var mOnclickListenLiser:((cardIndex:Int)->Unit)? = null
    fun setOnCardClickListener(l:(cardIndex:Int)->Unit) {
        mOnclickListenLiser = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 28
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //position就是第几张牌   holder就是button

        Log.d("xxx","$position")
        val card = Game.dates.get(position)
        holder.cardButton.isEnabled=!card.ischoose
        if (card.istoday){
            holder.cardButton.text = card.toString()
            holder.cardButton.setBackgroundColor(Color.WHITE)
        }else if (card.ischoose){
            holder.cardButton.text = card.toString()
            holder.cardButton.setBackgroundColor(Color.GREEN)
        }
        else{
            holder.cardButton.text = card.toString()
            holder.cardButton.setBackgroundColor(Color.GRAY)
        }
        holder.cardButton.setOnClickListener{
            mOnclickListenLiser?.invoke(position)
        }

    }
}