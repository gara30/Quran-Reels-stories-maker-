package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.QuranAudioPlayer
import com.example.data.AiBackgroundService
import com.example.data.api.QuranRepository
import com.example.data.model.Ayah
import com.example.data.model.BackgroundOption
import com.example.data.model.BackgroundType
import com.example.data.model.FontType
import com.example.data.model.Reciter
import com.example.data.model.StudioConfig
import com.example.data.model.Surah
import com.example.data.model.TranslationOption
import com.example.data.source.QuranDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class StudioSheetType {
    SURAH_VERSE,
    RECITER,
    TRANSLATION,
    TRANSITIONS,
    TYPOGRAPHY,
    BACKGROUND,
    AI_GENERATOR,
    EXPORT_SHARE,
    FEEDBACK
}

class AyatStudioViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository = QuranRepository()
    val audioPlayer = QuranAudioPlayer(application)
    val aiBackgroundService = AiBackgroundService()

    private val initialSurah = QuranDataSource.allSurahs.first() // Al-Fatihah
    private val initialReciter = QuranDataSource.reciters.first() // Mishary Alafasy
    private val initialBackground = QuranDataSource.defaultBackgrounds.first()
    private val initialTranslation = QuranDataSource.defaultTranslations.first()

    private val _config = MutableStateFlow(
        StudioConfig(
            selectedSurah = initialSurah,
            startVerse = 1,
            endVerse = 7.coerceAtMost(initialSurah.versesCount),
            selectedReciter = initialReciter,
            selectedTranslation = initialTranslation,
            selectedBackground = initialBackground
        )
    )
    val config: StateFlow<StudioConfig> = _config.asStateFlow()

    private val _versesList = MutableStateFlow<List<Ayah>>(emptyList())
    val versesList: StateFlow<List<Ayah>> = _versesList.asStateFlow()

    private val _activeVerseNumber = MutableStateFlow(1)
    val activeVerseNumber: StateFlow<Int> = _activeVerseNumber.asStateFlow()

    private val _isLoadingVerses = MutableStateFlow(false)
    val isLoadingVerses: StateFlow<Boolean> = _isLoadingVerses.asStateFlow()

    private val _activeSheet = MutableStateFlow<StudioSheetType?>(null)
    val activeSheet: StateFlow<StudioSheetType?> = _activeSheet.asStateFlow()

    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying
    val currentPlayingVerse: StateFlow<Int> = audioPlayer.currentPlayingVerse
    val playbackProgress: StateFlow<Float> = audioPlayer.progressFraction

    init {
        loadVersesForCurrentSurah()
        observeAudioPlayer()
    }

    private fun observeAudioPlayer() {
        viewModelScope.launch {
            audioPlayer.currentPlayingVerse.collect { verseNum ->
                _activeVerseNumber.value = verseNum
            }
        }
    }

    private fun loadVersesForCurrentSurah() {
        val currentSurah = _config.value.selectedSurah
        val currentTranslation = _config.value.selectedTranslation
        viewModelScope.launch {
            _isLoadingVerses.value = true
            val verses = quranRepository.getVersesForSurah(
                surahNumber = currentSurah.number,
                translationId = currentTranslation.resourceId,
                langCode = currentTranslation.id
            )
            _versesList.value = verses
            _isLoadingVerses.value = false

            // Update audio player bounds
            audioPlayer.prepareVerseRange(
                surahNumber = currentSurah.number,
                startVerse = _config.value.startVerse,
                endVerse = _config.value.endVerse,
                reciter = _config.value.selectedReciter
            )
        }
    }

    fun selectSurah(surah: Surah) {
        val defaultEnd = 5.coerceAtMost(surah.versesCount)
        _config.value = _config.value.copy(
            selectedSurah = surah,
            startVerse = 1,
            endVerse = defaultEnd
        )
        _activeVerseNumber.value = 1
        loadVersesForCurrentSurah()
    }

    fun setVerseRange(start: Int, end: Int) {
        val maxVerses = _config.value.selectedSurah.versesCount
        val validStart = start.coerceIn(1, maxVerses)
        val validEnd = end.coerceIn(validStart, maxVerses)

        _config.value = _config.value.copy(
            startVerse = validStart,
            endVerse = validEnd
        )
        if (_activeVerseNumber.value !in validStart..validEnd) {
            _activeVerseNumber.value = validStart
        }

        audioPlayer.prepareVerseRange(
            surahNumber = _config.value.selectedSurah.number,
            startVerse = validStart,
            endVerse = validEnd,
            reciter = _config.value.selectedReciter
        )
    }

    fun selectReciter(reciter: Reciter) {
        _config.value = _config.value.copy(selectedReciter = reciter)
        audioPlayer.prepareVerseRange(
            surahNumber = _config.value.selectedSurah.number,
            startVerse = _config.value.startVerse,
            endVerse = _config.value.endVerse,
            reciter = reciter
        )
    }

    fun selectTranslation(translation: TranslationOption) {
        _config.value = _config.value.copy(selectedTranslation = translation)
        loadVersesForCurrentSurah()
    }

    fun selectFont(fontType: FontType) {
        _config.value = _config.value.copy(fontType = fontType)
    }

    fun setFontSize(size: Float) {
        _config.value = _config.value.copy(fontSizeSp = size)
    }

    fun setTranslationFontSize(size: Float) {
        _config.value = _config.value.copy(translationFontSizeSp = size)
    }

    fun setFontColor(color: Color) {
        _config.value = _config.value.copy(fontColor = color)
    }

    fun setOverlayDarkness(darkness: Float) {
        _config.value = _config.value.copy(overlayDarkness = darkness)
    }

    fun selectTransition(transition: VerseTransitionType) {
        _config.value = _config.value.copy(transitionType = transition)
    }

    fun setTransitionDuration(durationMs: Int) {
        _config.value = _config.value.copy(transitionDurationMs = durationMs)
    }

    fun selectBackground(background: BackgroundOption) {
        _config.value = _config.value.copy(selectedBackground = background)
    }

    fun setGalleryBackground(uri: Uri) {
        val bgOption = BackgroundOption(
            id = "gallery_${System.currentTimeMillis()}",
            title = "صورة من معرض الهاتف",
            category = "معرض الهاتف",
            type = BackgroundType.GALLERY_MEDIA,
            mediaUri = uri
        )
        selectBackground(bgOption)
    }

    fun addGeneratedAiBackground(bitmap: Bitmap) {
        val bgOption = aiBackgroundService.storeGeneratedBitmap(
            key = "ai_bg_${System.currentTimeMillis()}",
            bitmap = bitmap
        )
        selectBackground(bgOption)
    }

    fun toggleTranslation() {
        _config.value = _config.value.copy(showTranslation = !_config.value.showTranslation)
    }

    fun toggleShowArabicText() {
        _config.value = _config.value.copy(showArabicText = !_config.value.showArabicText)
    }

    fun toggleWaveform() {
        _config.value = _config.value.copy(showWaveform = !_config.value.showWaveform)
    }

    fun toggleSurahHeader() {
        _config.value = _config.value.copy(showSurahHeader = !_config.value.showSurahHeader)
    }

    fun playPause() {
        audioPlayer.playPause()
    }

    fun nextVerse() {
        val next = _activeVerseNumber.value + 1
        if (next <= _config.value.endVerse) {
            _activeVerseNumber.value = next
            if (isPlaying.value) {
                audioPlayer.playVerse(next)
            }
        }
    }

    fun previousVerse() {
        val prev = _activeVerseNumber.value - 1
        if (prev >= _config.value.startVerse) {
            _activeVerseNumber.value = prev
            if (isPlaying.value) {
                audioPlayer.playVerse(prev)
            }
        }
    }

    fun setActiveVerse(verseNumber: Int) {
        if (verseNumber in _config.value.startVerse.._config.value.endVerse) {
            _activeVerseNumber.value = verseNumber
            if (isPlaying.value) {
                audioPlayer.playVerse(verseNumber)
            }
        }
    }

    fun openSheet(sheetType: StudioSheetType) {
        _activeSheet.value = sheetType
    }

    fun closeSheet() {
        _activeSheet.value = null
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
