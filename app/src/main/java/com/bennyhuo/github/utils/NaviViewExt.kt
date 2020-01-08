package com.bennyhuo.github.utils

import android.view.View
import androidx.core.view.ViewCompat
import com.bennyhuo.github.common.ext.otherwise
import com.bennyhuo.github.common.ext.yes
import com.google.android.material.navigation.NavigationView

inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                block()
            }

        })
    }
}