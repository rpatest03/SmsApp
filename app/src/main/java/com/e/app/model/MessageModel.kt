package com.e.app.model

import java.io.Serializable


data class MessageModel(
    var email: String = "",
    val msg: String = ""
):Serializable