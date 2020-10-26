package com.e.app.database

import com.e.app.model.MessageModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*

class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()

    private val firebaseDatabaseMessage = database.getReference("Message")

    fun addMessage(token:String,model:MessageModel,status: MessageStatus) {
        firebaseDatabaseMessage.push().key?.let {
            firebaseDatabaseMessage.child(token).child(it)
                .setValue(model).addOnCompleteListener(OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful) {
                        status.onSuccess()
                    } else {
                        status.onError()
                    }
                })
        }

    }
    interface MessageStatus {
        fun onSuccess()
        fun onError()
    }
}