package com.rober.simpletodonotes.util

import com.rober.simpletodonotes.model.Note

sealed class Event<T>(
     val message: String ? = null,
     val data: T? = null
){
    class Delete<T>(message: String, data: T) : Event<T>(message, data)
    class ErrorDelete<T>(message: String) : Event<T>(message)
    class Insert<T>(message: String) : Event<T>(message)
    class Update<T>(message:String) : Event<T>(message)
}