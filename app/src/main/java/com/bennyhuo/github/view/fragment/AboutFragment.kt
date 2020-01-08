package com.bennyhuo.github.view.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bennyhuo.github.R
import com.bennyhuo.github.utils.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.androidx.nestedScrollView
import org.jetbrains.anko.sdk15.listeners.onClick

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return AboutFragmentUI().createView(AnkoContext.create(context!!, this))
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

//    class AboutFragmentUI : AnkoComponent<AboutFragment> {
//        override fun createView(ui: AnkoContext<AboutFragment>): View = ui.apply {
//            nestedScrollView {
//                verticalLayout {
//                    imageView {
//                        imageResource = R.mipmap.ic_launcher
//                    }.lparams(width = wrapContent, height = wrapContent) {
//                        gravity = Gravity.CENTER_HORIZONTAL
//                    }
//
//                    themedTextView("GitHub", R.style.detail_title).lparams(
//                        width = wrapContent,
//                        height = wrapContent
//                    ) {
//                        gravity = Gravity.CENTER_HORIZONTAL
//                    }
//
//                    themedTextView(
//                        "By Bennyhuo",
//                        R.style.detail_description
//                    ).lparams(width = wrapContent, height = wrapContent) {
//                        gravity = Gravity.CENTER_HORIZONTAL
//                    }
//
//                    themedTextView(R.string.open_source_licenses, R.style.detail_description) {
//                        onClick {
//                            alert {
//                                customView {
//                                    scrollView {
//                                        textView {
//                                            padding = dip(10)
//                                            markdownText =
//                                                context.assets.open("licenses.md").bufferedReader()
//                                                    .readText()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }.lparams(width = wrapContent, height = wrapContent) {
//                        gravity = Gravity.CENTER_HORIZONTAL
//                    }
//                }.lparams(width = wrapContent, height = wrapContent) {
//                    gravity = Gravity.CENTER
//                }
//            }
//        }.view
//
//    }

}

