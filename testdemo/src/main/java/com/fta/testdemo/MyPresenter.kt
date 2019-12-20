package com.fta.testdemo

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.bennyhuo.mvp.impl.BasePresenter

class MyPresenter :BasePresenter<MyFragment>(){

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "MyPresenter-onConfigurationChanged: ");
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "MyPresenter-onSaveInstanceState: ");
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "MyPresenter-onViewStateRestored: ");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "MyPresenter-onCreate: ${ view.getInfo()}");

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "MyPresenter-onStart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "MyPresenter-onResume: ");
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "MyPresenter-onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "MyPresenter-onStop: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "MyPresenter-onDestroy: ");
    }


}