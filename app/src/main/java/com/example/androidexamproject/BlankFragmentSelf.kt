package com.example.androidexamproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_blank_self.*


class BlankFragmentSelf : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank_self, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSqLiteHelper = this.context?.let { MyOpenSqLiteHelper(it, 10)}
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }

        textView_addarea.setOnClickListener {
            val intent = Intent(activity,MainActivity_add_area::class.java)
            startActivity(intent)
        }

        textView_changeDefault.setOnClickListener {
            val intent = Intent(activity,MainActivity_changeDefault::class.java)
            startActivity(intent)
        }

        textView_del.setOnClickListener {
            val intent = Intent(activity,MainActivity_del::class.java)
            startActivity(intent)
        }

    }

}