package com.bennyhuo.github.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bennyhuo.github.R
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat


class MainActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

}