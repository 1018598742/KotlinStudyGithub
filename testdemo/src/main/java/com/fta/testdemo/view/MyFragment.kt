package com.fta.testdemo.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bennyhuo.mvp.impl.BaseFragment
import com.fta.testdemo.R
import com.fta.testdemo.network.entities.Person
import com.fta.testdemo.network.services.TestService
import com.fta.testdemo.presenter.MyPresenter
import com.fta.testdemo.utils.toStringMap
import kotlinx.android.synthetic.main.fragment_demo.*

class MyFragment : BaseFragment<MyPresenter>() {

    companion object {
        val TAG = MyFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutLogin.setOnClickListener {
            //            TestService.deviceCheck(SzgaAllocateCheckBean())
//                .subscribe({
//                    Log.i(TAG, "MyFragment-onViewCreated: result=${it}");
//                }, {
//                    Log.e(TAG, "MyFragment-onViewCreated: error=${it.printStackTrace()}");
//                })

            TestService.testData(toStringMap(Person()))
                .subscribe({
                    Log.i(TAG, "MyFragment-onViewCreated: person resulet=${it}");
                }, {
                    Log.e(TAG, "MyFragment-onViewCreated: error=${it.printStackTrace()}");
                })

        }
    }

    fun getInfo() = "呵呵呵"
}