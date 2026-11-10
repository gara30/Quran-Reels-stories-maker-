package com.example.data

import android.graphics.Bitmap
import android.util.Log
import com.example.data.model.BackgroundOption
import com.example.data.model.BackgroundType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

data class AiPromptSuggestion(
    val titleArabic: String,
    val promptEnglish: String
)

class AiBackgroundService {

    val promptSuggestions = listOf(
        AiPromptSuggestion(
            titleArabic = "سماء ليلية مرصعة بالنجوم وكثبان رملية هادئة",
            promptEnglish = "Spiritual calm night sky with glowing stars and gentle sand dunes in moonlight, 9:16 vertical orientation, cinematic atmosphere"
        ),
        AiPromptSuggestion(
            titleArabic = "جبال شاهقة في ضباب الفجر وشروق ذهبي",
            promptEnglish = "Majestic mountain peaks covered in soft dawn fog with golden sunlight rays, peaceful spiritual atmosphere, vertical 9:16"
        ),
        AiPromptSuggestion(
            titleArabic = "عمارة إسلامية عريقة بقباب وأقواس ذهبية",
            promptEnglish = "Historic Islamic mosque architecture with grand arches and golden illuminated domes under twilight sky, 9:16 vertical"
        ),
        AiPromptSuggestion(
            titleArabic = "شلالات هادئة وطبيعة خضراء غنّاء",
            promptEnglish = "Serene emerald waterfall flowing in lush green paradise valley with soft misty light, 9:16 vertical cinematic"
        ),
        AiPromptSuggestion(
            titleArabic = "أمواج بحر هادئة تحت ضوء القمر المكتمل",
            promptEnglish = "Tranquil ocean waves gently rolling on shore under luminous full moon, reflective water, 9:16 vertical peaceful"
        )
    )

    // In-memory generated backgrounds
    private val generatedBitmaps = mutableMapOf<String, Bitmap>()

    fun storeGeneratedBitmap(key: String, bitmap: Bitmap): BackgroundOption {
        generatedBitmaps[key] = bitmap
        return BackgroundOption(
            id = key,
            title = "خلفية ذكاء اصطناعي",
            category = "ذكاء اصطناعي",
            type = BackgroundType.AI_GENERATED,
            remoteOrBitmapKey = key
        )
    }

    fun getGeneratedBitmap(key: String): Bitmap? = generatedBitmaps[key]
}
