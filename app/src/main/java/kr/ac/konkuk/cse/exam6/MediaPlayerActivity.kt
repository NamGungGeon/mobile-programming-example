package kr.ac.konkuk.cse.exam6

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.exam4.VolumeControlView

open class MediaPlayerActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null
    var vol = 0.0f
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediaplayer)

        init()
    }

    override fun onResume() {
        super.onResume()

        if (flag)
            mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    fun init() {
        val volControlView = findViewById<VolumeControlView>(R.id.controller)
        volControlView.setVolumeListener(object : VolumeControlView.VolumeListener {
            override fun onChanged(angle: Float) {
                vol = if (angle > 0) angle / 360 else (360 + angle) / 360
                mediaPlayer?.setVolume(vol, vol)
            }
        })

        val playBtn = findViewById<ImageButton>(R.id.playBtn)
        playBtn.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.song)
                mediaPlayer?.setVolume(vol, vol)
            }
            mediaPlayer?.start()
            flag = true
        }
        val stopBtn = findViewById<ImageButton>(R.id.stopBtn)
        stopBtn.setOnClickListener {
            mediaPlayer?.pause()
            flag = false
        }
        val pauseBtn = findViewById<ImageButton>(R.id.pauseBtn)
        pauseBtn.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            flag = false
        }
    }
}