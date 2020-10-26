package com.e.app.utils


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class RequestPermission(private val m_Activity: Activity, isFragment: Boolean) {
    private var mPermissionCallBack: PermissionCallBack? = null

    fun checkPermission(permissionName: String): Boolean {
        return ContextCompat.checkSelfPermission(m_Activity, permissionName) == PackageManager.PERMISSION_GRANTED
    }

    fun permissionRequestShow(callBack: PermissionCallBack, vararg permissionName: String) {
        mPermissionCallBack = callBack
        ActivityCompat.requestPermissions(m_Activity, permissionName, REQUEST_CODE_PERMISSION)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == REQUEST_CODE_PERMISSION) {
            var grantAllPermission = true
            val permissionResultList = ArrayList<Boolean>()
            var deniedAllPermission = true

            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    permissionResultList.add(true)
                    deniedAllPermission = false
                } else {
                    permissionResultList.add(false)
                    grantAllPermission = false
                }
            }
            mPermissionCallBack!!.callBack(grantAllPermission, deniedAllPermission, permissionResultList)
            mPermissionCallBack = null
        }
    }


    interface PermissionCallBack {

        fun callBack(grantAllPermission: Boolean, deniedAllPermission: Boolean, permissionResultList: List<Boolean>)
    }

    companion object {
        const val READ_SMS = Manifest.permission.READ_SMS
        const val REQUEST_CODE_PERMISSION = 143

    }

}
