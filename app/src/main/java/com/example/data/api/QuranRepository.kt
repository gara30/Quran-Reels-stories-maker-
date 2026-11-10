package com.example.data.api

import com.example.data.model.Ayah
import com.example.data.source.QuranDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class QuranRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    // In-memory cache for fetched surahs keyed by "surahNumber_translationId"
    private val memoryCache = mutableMapOf<String, List<Ayah>>()

    suspend fun getVersesForSurah(
        surahNumber: Int,
        translationId: Int = 131,
        langCode: String = "en"
    ): List<Ayah> = withContext(Dispatchers.IO) {
        val cacheKey = "${surahNumber}_$translationId"
        // 1. Check in-memory cache
        memoryCache[cacheKey]?.let { return@withContext it }

        // 2. Fetch from Quran.com API v4 with chosen translationId
        try {
            val url = "https://api.quran.com/api/v4/verses/by_chapter/$surahNumber?language=ar&words=false&translations=$translationId&fields=text_uthmani&per_page=300"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val body = response.body?.string()
                if (!body.isNullOrBlank()) {
                    val json = JSONObject(body)
                    val versesArray = json.optJSONArray("verses")
                    if (versesArray != null && versesArray.length() > 0) {
                        val result = mutableListOf<Ayah>()
                        for (i in 0 until versesArray.length()) {
                            val vObj = versesArray.getJSONObject(i)
                            val verseNumber = vObj.optInt("verse_number", i + 1)
                            val id = vObj.optInt("id", verseNumber)
                            val textUthmani = vObj.optString("text_uthmani", "")
                            var translation = ""
                            val transArray = vObj.optJSONArray("translations")
                            if (transArray != null && transArray.length() > 0) {
                                translation = transArray.getJSONObject(0).optString("text", "")
                                    // Remove html tags if any
                                    .replace(Regex("<[^>]*>"), "")
                            }
                            result.add(
                                Ayah(
                                    surahNumber = surahNumber,
                                    numberInSurah = verseNumber,
                                    numberInQuran = id,
                                    textUthmani = if (textUthmani.isNotBlank()) textUthmani else "الآية رقم $verseNumber",
                                    translation = translation,
                                    translationsMap = mapOf(langCode to translation)
                                )
                            )
                        }
                        if (result.isNotEmpty()) {
                            memoryCache[cacheKey] = result
                            return@withContext result
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 3. Fallback: Check bundled local verses
        val bundled = QuranDataSource.getBundledVerses(surahNumber)
        if (bundled != null && bundled.isNotEmpty()) {
            memoryCache[cacheKey] = bundled
            return@withContext bundled
        }

        // 4. Fallback: generate placeholder verses with surah count
        val surahInfo = QuranDataSource.allSurahs.find { it.number == surahNumber }
        val count = surahInfo?.versesCount ?: 7
        val fallback = (1..count).map { vNum ->
            Ayah(
                surahNumber = surahNumber,
                numberInSurah = vNum,
                numberInQuran = vNum,
                textUthmani = "سورة ${surahInfo?.nameArabic ?: ""} - آية $vNum",
                translation = "Surah ${surahInfo?.nameEnglish ?: ""} - Verse $vNum",
                translationsMap = mapOf(langCode to "Surah ${surahInfo?.nameEnglish ?: ""} - Verse $vNum")
            )
        }
        fallback
    }
}
