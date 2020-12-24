package com.example.androidexamproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexamproject.date.DateG
import kotlinx.android.synthetic.main.date_item.*
import kotlinx.android.synthetic.main.fragment_cloud.*
import kotlinx.android.synthetic.main.fragment_date.*
import java.util.*


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
        textView_year.text = "2020"
        val recycle = view.findViewById<RecyclerView>(R.id.recyckerView_date)
        game=DateG()
        adapter= DateCardAdapter(game)
        recycle.adapter = adapter

        recycle.layoutManager = GridLayoutManager(this.context,7)
        adapter.notifyDataSetChanged()
        adapter.setOnCardClickListener {
            game.chooseCard(it)
            adapter.notifyDataSetChanged()
            editText_beiwang.hint = game.dates[it].beiwang
        }


    }
}