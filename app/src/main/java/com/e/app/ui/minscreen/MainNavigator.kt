package com.e.app.ui.minscreen
import com.e.app.base.BaseNavigator
interface MainNavigator : BaseNavigator {
    fun onSuccess()

    fun onFail()

    fun onConnectButtonClickHandle()

}