package com.kuma.kangaroof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView.setVideoPath("https://media.w3.org/2010/05/sintel/trailer.mp4")
    }
}