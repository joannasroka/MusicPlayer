package com.example.musicplayer

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var playWhenReady: Boolean = true

    var playbackPosition: Long = 0L

    var currentWindow: Int = 0
}