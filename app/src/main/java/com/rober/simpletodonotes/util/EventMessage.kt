package com.rober.simpletodonotes.util

sealed class EventMessage<T>(
    val message: String
) {
    class EmptyFields<T>(message: String): EventMessage<T>(message)
}