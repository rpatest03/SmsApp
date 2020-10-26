package com.e.app.ui.minscreen

import android.app.Application
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.MessageModel
import com.e.app.utils.Session
import java.util.regex.Pattern


class MainViewModel(application: Application,session: Session) : BaseViewModel<MainNavigator>(application,session) {

    val databaseHelper = FirebaseDatabaseHelper()

    var p: Pattern = Pattern.compile(
        "[a-zA-Z0-9]"
                + "[a-zA-Z0-9_.]"
                + "*@[a-zA-Z0-9]"
                + "+([.][a-zA-Z]+)+"
    )
    fun onConnectButtonClick()
    {
        getNavigator()?.onConnectButtonClickHandle()
    }
    fun addMessage(model:MessageModel)
    {
        databaseHelper.addMessage(getSession().getString(),model,object :FirebaseDatabaseHelper.MessageStatus{
            override fun onSuccess() {
                getNavigator()?.onSuccess()
            }

            override fun onError() {
                getNavigator()?.onFail()
            }
        })
    }
}