package com.loner.android.sdk.utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.loner.android.sdk.R

class SoundManager private constructor(context: Context) {
    private var mMediaPlayer: MediaPlayer? = null
    private var mContext: Context? = null
    private var alertSound: Uri? = null
    private var checkInSound: Uri? = null

    init {
        mContext = context
        alertSound = Uri.parse("""android.resource://${context?.packageName}/${R.raw.alarm}""")
        checkInSound = Uri.parse("""android.resource://${context?.packageName}/${R.raw.missed_checkin_alert_msg}""")
    }

    companion object {

        @Volatile
        private var INSTANCE: SoundManager? = null

        fun getInstance(context: Context): SoundManager {
            return INSTANCE ?: synchronized(this) {
                SoundManager(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    fun stopSound() {
        mMediaPlayer?.let {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    fun playSoundForAlert() {
        stopSound()
        playSound(alertSound!!)

    }

    fun playSoundForCheckIn() {
        stopSound()
        playSound(checkInSound!!)
    }

    private fun playSound(soundUrl: Uri) {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.let {
            mMediaPlayer!!.setDataSource(mContext, soundUrl)
            mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_RING)
            mMediaPlayer!!.prepare()
            mMediaPlayer!!.start()
            mMediaPlayer!!.isLooping = true
        }
    }

}