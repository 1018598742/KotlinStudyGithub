package com.fta.testdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bennyhuo.mvp.impl.BaseFragment

class MyFragment : BaseFragment<MyPresenter>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    fun getInfo() = "呵呵呵"
}