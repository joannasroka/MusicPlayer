// Joanna Sroka 246756
// Testowane na Xiaomi Redmi 6

package com.example.musicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
// import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var playerService: PlayerService? = null

    //private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setSupportActionBar(findViewById(R.id.toolbar))
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayerService.PlayerServiceBinder
            playerService = binder.service

            binding.playerView.player = binder.player
            binding.titleTV.text =
                binder.player.currentMediaItem?.mediaMetadata?.title ?: ""

            binder.player.addListener(object : Player.EventListener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    binding.titleTV.text = mediaItem?.mediaMetadata?.title ?: ""
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            playerService = null
        }
    }

    private fun bindToPlayerService() {
        if (playerService == null) {
            val intent = Intent(this, PlayerService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        bindToPlayerService()

       /* if (Util.SDK_INT >= 24) {
            initializePlayer()
        }*/
    }

  /*  override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding.content.playerView.player = player
        for (song in Songs.sampleTracks) {
            val item = MediaItem.fromUri(song.uri)
                .buildUpon()
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(song.title)
                        .build())
                .build()
            player?.addMediaItem(item)
        }
        player?.playWhenReady = viewModel.playWhenReady
        player?.seekTo(viewModel.currentWindow, viewModel.playbackPosition)
        player?.prepare()
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            viewModel.playWhenReady = player!!.playWhenReady
            viewModel.playbackPosition = player!!.currentPosition
            viewModel.currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding.content.playerView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }*/


}