package com.e.app.ui.minscreen

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivitySplashBinding
import com.e.app.model.MessageModel
import com.e.app.utils.RequestPermission
import com.e.app.utils.ViewModelProviderFactory
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import org.koin.android.ext.android.inject
import java.util.regex.Matcher


class MainActivity : BaseActivity<ActivitySplashBinding, MainViewModel>(), MainNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

    private var activityLoginBinding: ActivitySplashBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        getToken()
    }


    fun refreshInbox() {
        val cResolver = contentResolver
        val smsInboxCursor: Cursor? = cResolver.query(
            Uri.parse("content://sms/inbox"),
            null, null, null, null
        )
        val indexBody: Int = smsInboxCursor!!.getColumnIndex("body")
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return
        do {
           var messageBody = smsInboxCursor.getString(indexBody)

            if(messageBody.contains("EbixCash eGift"))
            {
                val m: Matcher = viewModel.p.matcher(messageBody)
                while (m.find()) {
                    var model=MessageModel(m.group(),messageBody)
                    viewModel.addMessage(model)
                }

            }

        } while (smsInboxCursor!!.moveToNext())
    }

    private fun openRequestPermission() {
        if (requestPermission!!.checkPermission(RequestPermission.READ_SMS)) {
            refreshInbox()
        } else {
            requestPermission!!.permissionRequestShow(
                object : RequestPermission.PermissionCallBack {
                    override fun callBack(
                        grantAllPermission: Boolean,
                        deniedAllPermission: Boolean,
                        permissionResultList: List<Boolean>
                    ) {
                        if (grantAllPermission)  refreshInbox()
                    }
                },

                RequestPermission.READ_SMS
            )
        }
    }

    fun getToken()
    {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener(this@MainActivity,
                OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                   viewModel.getSession().setAppUserToken(instanceIdResult.token)
                })
    }

    override fun onSuccess() {

    }

    override fun onFail() {

    }

    override fun onConnectButtonClickHandle() {
        openRequestPermission()
    }

}
