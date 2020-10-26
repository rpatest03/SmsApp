package com.e.app.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.app.ui.minscreen.MainViewModel

class ViewModelProviderFactory(application: Application, session: Session) :
    ViewModelProvider.Factory {

    private var application: Application = application

    private var session: Session = session

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application,session) as T

            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}