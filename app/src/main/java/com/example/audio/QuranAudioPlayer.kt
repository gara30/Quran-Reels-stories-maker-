package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.data.model.Reciter
import com.example.data.source.QuranDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuranAudioPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPlayingVerse = MutableStateFlow(1)
    val currentPlayingVerse: StateFlow<Int> = _currentPlayingVerse.asStateFlow()

    private val _progressFraction = MutableStateFlow(0f)
    val progressFraction: StateFlow<Float> = _progressFraction.asStateFlow()

    private var currentSurahNumber: Int = 1
    private var currentStartVerse: Int = 1
    private var currentEndVerse: Int = 7
    private var currentReciter: Reciter = QuranDataSource.reciters.first()

    private val progressRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let { player ->
                try {
                    if (player.isPlaying && player.duration > 0) {
                        _progressFraction.value = (player.currentPosition.toFloat() / player.duration.toFloat()).coerceIn(0f, 1f)
                    }
                } catch (e: Exception) {
                    // Ignore transient illegal state
                }
            }
            if (_isPlaying.value) {
                handler.postDelayed(this, 100)
            }
        }
    }

    fun prepareVerseRange(
        surahNumber: Int,
        startVerse: Int,
        endVerse: Int,
        reciter: Reciter
    ) {
        val sameSurahAndRange = (currentSurahNumber == surahNumber &&
                currentStartVerse == startVerse &&
                currentEndVerse == endVerse &&
                currentReciter.id == reciter.id)

        currentSurahNumber = surahNumber
        currentStartVerse = startVerse
        currentEndVerse = endVerse
        currentReciter = reciter

        if (!sameSurahAndRange) {
            stop()
            _currentPlayingVerse.value = startVerse
            _progressFraction.value = 0f
        }
    }

    fun playPause() {
        if (_isPlaying.value) {
            pause()
        } else {
            val verseToPlay = if (_currentPlayingVerse.value in currentStartVerse..currentEndVerse) {
                _currentPlayingVerse.value
            } else {
                currentStartVerse
            }
            playVerse(verseToPlay)
        }
    }

    fun playVerse(verseNumber: Int) {
        _currentPlayingVerse.value = verseNumber
        releasePlayer()

        val audioUrl = QuranDataSource.getAudioUrl(currentReciter, currentSurahNumber, verseNumber)
        Log.d("QuranAudioPlayer", "Playing audio URL: $audioUrl")

        try {
            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(audioUrl)
                setOnPreparedListener { mp ->
                    mp.start()
                    _isPlaying.value = true
                    handler.post(progressRunnable)
                }
                setOnCompletionListener {
                    onVerseCompleted()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("QuranAudioPlayer", "MediaPlayer error: what=$what, extra=$extra")
                    _isPlaying.value = false
                    true
                }
                prepareAsync()
            }
            mediaPlayer = player
        } catch (e: Exception) {
            Log.e("QuranAudioPlayer", "Failed to initialize player", e)
            _isPlaying.value = false
        }
    }

    private fun onVerseCompleted() {
        val nextVerse = _currentPlayingVerse.value + 1
        if (nextVerse <= currentEndVerse) {
            playVerse(nextVerse)
        } else {
            // Loop back to start or pause
            _currentPlayingVerse.value = currentStartVerse
            _progressFraction.value = 0f
            _isPlaying.value = false
            handler.removeCallbacks(progressRunnable)
        }
    }

    fun nextVerse() {
        val next = _currentPlayingVerse.value + 1
        if (next <= currentEndVerse) {
            playVerse(next)
        }
    }

    fun previousVerse() {
        val prev = _currentPlayingVerse.value - 1
        if (prev >= currentStartVerse) {
            playVerse(prev)
        }
    }

    fun pause() {
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
            // Ignore
        }
        _isPlaying.value = false
        handler.removeCallbacks(progressRunnable)
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
            // Ignore
        }
        _isPlaying.value = false
        _progressFraction.value = 0f
        handler.removeCallbacks(progressRunnable)
        releasePlayer()
    }

    private fun releasePlayer() {
        handler.removeCallbacks(progressRunnable)
        try {
            mediaPlayer?.release()
        } catch (e: Exception) {
            // Ignore
        }
        mediaPlayer = null
    }

    fun release() {
        stop()
    }
}
