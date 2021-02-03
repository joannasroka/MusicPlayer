package com.example.musicplayer

import android.net.Uri

object Songs {
    private const val root = "file:///android_asset/"

    val sampleTracks: List<Song> = listOf(
        Song("The Verve - Bitter Sweet symphony",
            Uri.parse(root + "verve_bitter_sweet_symphony.mp3")),
        Song("Arctic Monkeys - Do I wanna know",
            Uri.parse(root + "arctic_monkeys_do_i_wanna_know.mp3")),
        Song("Hey - Moja i twoja nadzieja",
            Uri.parse(root + "hey_moja_i_twoja_nadzieja.mp3")),
        Song("Yann Tiersen - Comptine d`un autre ete - l`apres-midi",
            Uri.parse(root + "Yann Tiersen - Comptine d`un autre ete - l`apres-midi.mp3")),
        Song("Hans Zimmer - Oogway Ascends Kung Fu Panda Soundtrack"
            ,Uri.parse(root + "Hans Zimmer - Oogway Ascends Kung Fu Panda Soundtrack.mp3"))
    )





}