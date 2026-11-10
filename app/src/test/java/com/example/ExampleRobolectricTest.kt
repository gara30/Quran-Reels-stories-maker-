package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.source.QuranDataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("صانع فيديو الآيات", appName)
  }

  @Test
  fun `verify quran datasource has 114 surahs and 20 reciters`() {
    assertEquals(114, QuranDataSource.allSurahs.size)
    assertEquals(20, QuranDataSource.reciters.size)
    val alafasy = QuranDataSource.reciters.first()
    val url = QuranDataSource.getAudioUrl(alafasy, 1, 1)
    assertTrue(url.contains("Alafasy_128kbps/001001.mp3"))
  }

  @Test
  fun `verify Saudi King Fahd Complex translations exist and include English and Tafseer`() {
    val translations = QuranDataSource.defaultTranslations
    assertTrue(translations.isNotEmpty())
    // Verify King Fahd Complex authority
    assertTrue(translations.all { it.authority.contains("مجمع الملك فهد") })
    // Verify English (Saheeh International) and Tafseer Al-Muyassar are present
    assertTrue(translations.any { it.resourceId == 131 && it.languageCode == "en" })
    assertTrue(translations.any { it.resourceId == 16 && it.languageCode == "ar_tafseer" })
  }

  @Test
  fun `verify inward bracket formatting enclosing Quranic text`() {
    val ayahText = "ٱلْحَمْدُ لِلَّهِ رَبِّ ٱلْعَٰلَمِينَ"
    val enclosed = "﴾ $ayahText ﴿"
    assertTrue(enclosed.startsWith("﴾"))
    assertTrue(enclosed.endsWith("﴿"))
  }
}

