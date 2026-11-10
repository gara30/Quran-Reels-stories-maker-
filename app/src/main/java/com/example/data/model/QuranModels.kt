package com.example.data.model

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val englishTranslation: String,
    val versesCount: Int,
    val revelationType: String // "Meccan" or "Medinan"
)

data class TranslationOption(
    val id: String,
    val resourceId: Int,
    val languageNameArabic: String,
    val languageNameNative: String,
    val flag: String,
    val authorName: String
) {
    val nameArabic: String get() = languageNameArabic
    val authority: String get() = authorName
    val languageCode: String get() = id
}

data class Ayah(
    val surahNumber: Int,
    val numberInSurah: Int,
    val numberInQuran: Int,
    val textUthmani: String,
    val translation: String = "",
    val translationsMap: Map<String, String> = emptyMap(),
    val audioUrl: String? = null
) {
    fun getTranslationFor(langId: String): String {
        return translationsMap[langId] ?: translation
    }
}

data class Reciter(
    val id: String,
    val nameArabic: String,
    val nameEnglish: String,
    val style: String,
    val audioFolder: String
)

enum class FontType(val displayNameArabic: String) {
    AMIRI("خط الأميري"),
    SCHEHERAZADE("خط شهرزاد"),
    SYSTEM("الخط القياسي")
}

enum class BackgroundType {
    DRAWABLE_PRESET,
    VIDEO_LOOP_PRESET,
    GRADIENT_PRESET,
    GALLERY_MEDIA,
    AI_GENERATED
}

enum class MotionAnimationType {
    NONE,
    STARRY_COSMOS,
    RAIN_DROPLETS,
    GOLDEN_DUST,
    AURORA_WAVE
}

data class BackgroundOption(
    val id: String,
    val title: String,
    val category: String, // "فيديوهات", "مساجد", "طبيعة", "سماء", "تدرجات"
    val type: BackgroundType,
    val isVideo: Boolean = false,
    val motionType: MotionAnimationType = MotionAnimationType.NONE,
    @DrawableRes val drawableRes: Int? = null,
    val videoUrl: String? = null,
    val gradientColors: List<Color>? = null,
    val mediaUri: Uri? = null,
    val remoteOrBitmapKey: String? = null
)

enum class VerseTransitionType(
    val id: String,
    val titleArabic: String,
    val descriptionArabic: String,
    val emoji: String
) {
    SMOOTH_FADE(
        id = "fade",
        titleArabic = "تلاشٍ هادئ (Fade)",
        descriptionArabic = "ظهور ناعم وتدريجي دون حركة مشتتة للعين",
        emoji = "🕊️"
    ),
    SLIDE_UP(
        id = "slide_up",
        titleArabic = "انزلاق للأعلى (Slide Up)",
        descriptionArabic = "صعود انسيابي وقور يرفع النص للأعلى بسلاسة",
        emoji = "⬆️"
    ),
    CINEMATIC_ZOOM(
        id = "zoom_in",
        titleArabic = "تقريب سينمائي (Cinematic Zoom)",
        descriptionArabic = "تكبير هادئ ووقور يعطي عمقاً فخماً للفيديو",
        emoji = "🔍"
    ),
    PAGE_SLIDE(
        id = "page_slide",
        titleArabic = "تقليب الصفحات (Horizontal Slide)",
        descriptionArabic = "انزلاق أفقي يحاكي تقليب صفحات المصحف الشريف",
        emoji = "📖"
    ),
    LUMINOUS_GLOW(
        id = "glow_reveal",
        titleArabic = "إشراق نوراني (Luminous Glow)",
        descriptionArabic = "ظهور الآيات مع هالة إشراق ذهبية إيمانية بديعة",
        emoji = "✨"
    ),
    BOUNCE_REVEAL(
        id = "spring_reveal",
        titleArabic = "انبثاق مرن (Spring Reveal)",
        descriptionArabic = "ظهور لطيف بتأثير زنبركي خفيف وجذاب",
        emoji = "🌟"
    )
}

data class StudioConfig(
    val selectedSurah: Surah,
    val startVerse: Int = 1,
    val endVerse: Int = 3,
    val selectedReciter: Reciter,
    val selectedTranslation: TranslationOption,
    val showTranslation: Boolean = true,
    val showArabicText: Boolean = true,
    val fontType: FontType = FontType.AMIRI,
    val fontSizeSp: Float = 26f,
    val translationFontSizeSp: Float = 14f,
    val fontColor: Color = Color.White,
    val overlayDarkness: Float = 0.45f,
    val selectedBackground: BackgroundOption,
    val showSurahHeader: Boolean = true,
    val showWaveform: Boolean = true,
    val transitionType: VerseTransitionType = VerseTransitionType.SLIDE_UP,
    val transitionDurationMs: Int = 600
)
