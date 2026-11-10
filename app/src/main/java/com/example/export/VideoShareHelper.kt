package com.example.export

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.example.data.model.Ayah
import com.example.data.model.Reciter
import com.example.data.model.Surah
import java.io.File
import java.io.FileOutputStream

object VideoShareHelper {

    fun shareAyahStory(
        context: Context,
        ayah: Ayah,
        surah: Surah,
        reciter: Reciter,
        storyBitmap: Bitmap?
    ) {
        val shareText = buildString {
            append("قال تعالى:\n")
            append("﴾ ${ayah.textUthmani} ﴿\n\n")
            if (ayah.translation.isNotBlank()) {
                append("\"${ayah.translation}\"\n\n")
            }
            append("[سورة ${surah.nameArabic} - الآية ${ayah.numberInSurah}]\n")
            append("بصوت القارئ: ${reciter.nameArabic}\n")
            append("صُمّم بواسطة تطبيق صانع فيديو الآيات")
        }

        if (storyBitmap != null) {
            try {
                val imagesFolder = File(context.cacheDir, "images").apply { mkdirs() }
                val imageFile = File(imagesFolder, "quran_verse_${surah.number}_${ayah.numberInSurah}.jpg")
                FileOutputStream(imageFile).use { out ->
                    storyBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
                }

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    imageFile
                )

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/jpeg"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(intent, "مشاركة قصة الآية القرآنية"))
                return
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback to text sharing
        val textIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(textIntent, "مشاركة الآية القرآنية"))
    }
}
