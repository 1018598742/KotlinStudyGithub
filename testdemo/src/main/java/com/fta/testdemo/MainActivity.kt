package com.fta.testdemo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fta.testdemo.view.MyFragment
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyPermissions.requestPermissions(
            this,
            "需要权限",
            0,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        supportFragmentManager.beginTransaction().add(
            R.id.container,
            MyFragment(), "MyFragment"
        )
            .commitAllowingStateLoss()
    }
}
