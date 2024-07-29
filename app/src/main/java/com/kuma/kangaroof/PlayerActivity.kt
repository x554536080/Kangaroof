package com.kuma.kangaroof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.kuma.kumamedia.player.media.IjkPlayerView

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
findViewById<IjkPlayerView>(R.id.playerView)
//        playerView
            .setVideoPath("https://media.w3.org/2010/05/sintel/trailer.mp4")
    }
}