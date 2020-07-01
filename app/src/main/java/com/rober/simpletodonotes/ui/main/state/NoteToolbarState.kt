package com.rober.simpletodonotes.ui.main.state

sealed class NoteToolbarState {

    class MultiSelectionState: NoteToolbarState() {
        override fun toString(): String {
            return "MultiSelectionState"
        }
    }

    class DefaultState: NoteToolbarState() {
        override fun toString(): String {
            return "DefaultState"
        }
    }
}