package com.bennyhuo.github.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.bennyhuo.github.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.navigation.ui.AppBarConfiguration as AppBarConfiguration1


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration1(
            topLevelDestinationIds = setOf(
                R.id.navRepos,
                R.id.navPeople,
                R.id.navIssue,
                R.id.navAbout
            ),
            drawerLayout = drawer_layout
        )

    }

}